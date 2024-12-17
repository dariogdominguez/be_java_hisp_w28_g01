package com.meli.be_java_hisp_w28_g01.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto implements Serializable {
    int id;
    String name;
    String type;
    String brand;
    String color;
    String notes;
}