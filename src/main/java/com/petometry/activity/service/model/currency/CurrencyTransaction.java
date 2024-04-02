package com.petometry.activity.service.model.currency;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CurrencyTransaction {

    private CurrencyType currency;

    private String source;

    private String target;

    private Double value;
}
