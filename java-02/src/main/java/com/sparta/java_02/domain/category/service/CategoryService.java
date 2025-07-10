package com.sparta.java_02.domain.category.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.java_02.common.exception.ServiceException;
import com.sparta.java_02.common.exception.ServiceExceptionCode;
import com.sparta.java_02.common.utils.JedisUtils;
import com.sparta.java_02.domain.category.dto.CategoryRequest;
import com.sparta.java_02.domain.category.dto.CategoryResponse;
import com.sparta.java_02.domain.category.entity.Category;
import com.sparta.java_02.domain.category.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

  private final Jedis jedis;
  private final JedisUtils jedisUtils;
  private final ObjectMapper objectMapper;
  private final CategoryRepository categoryRepository;
  private static final String CACHE_KEY_CATEGORY_STRUCT = "categoryStruct";

  public List<CategoryResponse> getCategoryStruct() {
    List<Category> categories = categoryRepository.findAll();

    // 2중 반복문을 해결하는 법
    Map<Long, CategoryResponse> categoryResponseMap = new HashMap<>();

    for (Category category : categories) {
      CategoryResponse response = CategoryResponse.builder()
          .id(category.getId())
          .name(category.getName())
          .categories(new ArrayList<>())
          .build();

      categoryResponseMap.put(category.getId(), response);
    }

    List<CategoryResponse> rootCategories = new ArrayList<>();
    for (Category category : categories) {
      CategoryResponse categoryResponse = categoryResponseMap.get(category.getId());

      if (ObjectUtils.isEmpty(category.getParent())) {
        rootCategories.add(categoryResponse);
      } else {
        CategoryResponse parentCategoryResponse = categoryResponseMap.get(
            category.getParent().getId());
        if (ObjectUtils.isEmpty(parentCategoryResponse)) {
          parentCategoryResponse.getCategories().add(categoryResponse);
        }
      }
    }
    return rootCategories;
  }

  @Transactional
  public List<CategoryResponse> findCategoryStructCacheAside() {
    String cachedCategories = jedis.get(CACHE_KEY_CATEGORY_STRUCT);

    try {
      if (StringUtils.hasText(cachedCategories)) {
        return objectMapper.readValue(cachedCategories,
            new TypeReference<List<CategoryResponse>>() {
            });
      }

      List<CategoryResponse> categories = getCategoryStruct();
      jedisUtils.saveObject(CACHE_KEY_CATEGORY_STRUCT, categories);
//      String jsonString = objectMapper.writeValueAsString(categories);
//      jedis.set(CACHE_KEY_CATEGORY_STRUCT, jsonString);

      return categories;
    } catch (Exception e) {
      throw new RuntimeException("JSON 파싱 버그");
    }
  }

  @Transactional
  public void saveWriteThrough(CategoryRequest request) {
    Category parentCategory = ObjectUtils.isEmpty(request)
        ? null
        : categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_DATA));

    Category category = Category.builder()
        .name(request.getName())
        .parent(parentCategory)
        .build();

    try {
      List<CategoryResponse> rootCategories = getCategoryStruct();

      if (!ObjectUtils.isEmpty(rootCategories)) {
        String jsonString = objectMapper.writeValueAsString(rootCategories);
      }
    } catch (Exception e) {
      log.error("Error updating cache key : {}: {} ", CACHE_KEY_CATEGORY_STRUCT, e.getMessage());
    }
  }


  @Transactional
  public void saveWriteBack(CategoryRequest request) {
    try {
      String cachedData = jedis.get(CACHE_KEY_CATEGORY_STRUCT);
      List<CategoryResponse> categories = new ArrayList<>();

      if (ObjectUtils.isEmpty(cachedData)) {
        categories = objectMapper.readValue(cachedData, new TypeReference<>() {
        });
      }

      CategoryResponse newCategory = CategoryResponse.builder()
          .name(request.getName())
          .categories(new ArrayList<>())
          .build();

      // TODO: 신규 카테고리는 부모 클래스 하위로 들어가도록 수정되어야 함
      categories.add(newCategory);

      String jsonString = objectMapper.writeValueAsString(categories);
      jedis.set(CACHE_KEY_CATEGORY_STRUCT, jsonString);

      saveToDatabaseAsync(request);
    } catch (Exception e) {
      log.error("Write-back 패턴 저장 실패 : {}", e.getMessage());
    }
  }

  @Async
  public void saveToDatabaseAsync(CategoryRequest request) {
    try {
      Category parentCategory = ObjectUtils.isEmpty(request)
          ? null
          : categoryRepository.findById(request.getCategoryId())
              .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_DATA));

      Category category = Category.builder()
          .name(request.getName())
          .parent(parentCategory)
          .build();

      categoryRepository.save(category);
    } catch (Exception e) {
      // 캐시 롤백 코드
      // retry 로직이 들어감
      //
    }
  }
}
