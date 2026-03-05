package com.conversordemonedas.modelo;

import com.google.gson.annotations.SerializedName;

public record ExchangeRatePairResponse(
        String result,
        @SerializedName("error-type") String errorType,
        @SerializedName("base_code") String baseCode,
        @SerializedName("target_code") String targetCode,
        @SerializedName("conversion_rate") double conversionRate
) {
}
