package com.petometry.activity.service.model.currency;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * DTO for {@link com.petometry.currencyservice.repository.model.PetFoodBalance}
 */
@Data
@ToString
public class CurrencyPetFoodBalances implements Serializable {

    @NotNull
    @Min(value = 0, message = "Circle can not be negative")
    private Long circle;

    @NotNull
    @Min(value = 0, message = "Triangle can not be negative")
    private Long triangle;

    @NotNull
    @Min(value = 0, message = "Rectangle can not be negative")
    private Long rectangle;
}