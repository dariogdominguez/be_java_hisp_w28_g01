package com.meli.be_java_hisp_w28_g01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromoPost extends Post{

    boolean hasPromo;
    double discount;
    // Constructor que inicializa todos los atributos de Post y PromoPost
    public PromoPost(Seller user, LocalDate date, Product product, int category, double price, boolean hasPromo, double discount) {
        super(user, date, product, category, price);
        this.hasPromo = hasPromo;
        this.discount = discount;
    }
}
