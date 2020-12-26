package com.bdg.bank.transaction.util;

import com.bdg.bank.transaction.domain.UserInfo;
import com.bdg.bank.transaction.entity.Authority;
import com.bdg.bank.transaction.entity.UserEntity;

public class UserConverter {

    public static UserEntity convertToUserEntity(UserInfo userInfo, Authority authority) {
        return UserEntity.builder()
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .authority(authority)
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .build();
    }

    public static UserInfo convertToUserInfo(UserEntity userInfo) {
        return UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .build();
    }
}
