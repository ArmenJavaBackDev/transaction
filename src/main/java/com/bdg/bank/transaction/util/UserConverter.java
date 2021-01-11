package com.bdg.bank.transaction.util;

import com.bdg.bank.transaction.dto.UserDto;
import com.bdg.bank.transaction.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserDto convertToUserInfo(UserEntity userInfo) {
        return UserDto.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .build();
    }

    public static List<UserDto> convertToUserList(List<UserEntity> users) {
        return users.stream()
                .map(UserConverter::convertToUserInfo)
                .collect(Collectors.toList());
    }
}
