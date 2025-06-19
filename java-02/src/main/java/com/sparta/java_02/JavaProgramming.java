package com.sparta.java_02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
class CategoryFlatDto {

  private Long id;
  private String name;
  private Long parentId;

  public CategoryFlatDto(Long id, String name, Long parentId) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
  }

  @Override
  public String toString() {
    return "CategoryFlatDto{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", parentId=" + parentId +
        '}';
  }
}

@Getter
class CategoryTreeDto {

  private Long id;
  private String name;
  private List<CategoryTreeDto> children = new ArrayList<>();

  public CategoryTreeDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public void addChildren(CategoryTreeDto categoryTreeDto) {
    this.children.add(categoryTreeDto);
  }

  @Override
  public String toString() {
    return "CategoryTreeDto{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", children=" + children +
        '}';
  }
}

public class JavaProgramming {

  public static void main(String[] args) {

    List<CategoryFlatDto> flatCategories = Arrays.asList(
        new CategoryFlatDto(1L, "1", null),
        new CategoryFlatDto(2L, "2", 1L),
        new CategoryFlatDto(3L, "3", 2L),
        new CategoryFlatDto(4L, "4", 3L),
        new CategoryFlatDto(5L, "5", 1L));

    Map<Long, CategoryTreeDto> map = new HashMap<>();

    for (CategoryFlatDto flatDto : flatCategories) {
      map.put(flatDto.getId(), flatToTree(flatDto));
    }

    for (CategoryFlatDto flatDto : flatCategories) {
      CategoryTreeDto currentFlatDto = map.get(flatDto.getId());

      CategoryTreeDto categoryTreeDto = map.get(flatDto.getParentId());
      if (categoryTreeDto != null) {
        categoryTreeDto.addChildren(currentFlatDto);
      }
    }

    map.forEach((k, v) -> System.out.println(k + ": " + v));

  }

  private static CategoryTreeDto flatToTree(CategoryFlatDto category) {
    return new CategoryTreeDto(
        category.getId(),
        category.getName()
    );
  }

}
