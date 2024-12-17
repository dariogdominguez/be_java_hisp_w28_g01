package com.meli.be_java_hisp_w28_g01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowersDto {
    int userId;
    String userName;
    int followersCount;
}