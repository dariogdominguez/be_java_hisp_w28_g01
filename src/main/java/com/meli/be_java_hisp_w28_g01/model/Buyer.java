package com.meli.be_java_hisp_w28_g01.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Buyer extends User{
}
