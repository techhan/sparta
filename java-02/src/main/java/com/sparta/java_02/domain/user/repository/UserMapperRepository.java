package com.sparta.java_02.domain.user.repository;


import com.sparta.java_02.domain.user.dto.SearchIUserDto;
import com.sparta.java_02.domain.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapperRepository {


    SearchIUserDto getUserById(Long id);

     void insertUser(UserDto user);

    void getUserId(Long id);

    void updateUser(UserDto user);

    void deleteUser(Long id);

}
