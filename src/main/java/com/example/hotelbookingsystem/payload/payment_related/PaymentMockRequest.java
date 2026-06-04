package com.example.hotelbookingsystem.payload.payment_related;

import com.example.hotelbookingsystem.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentMockRequest(
        @NotNull Long booingId,
        @NotNull Currency currency,
        Boolean success
) {
}
