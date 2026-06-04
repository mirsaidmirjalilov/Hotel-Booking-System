package com.example.hotelbookingsystem.payload.review_related;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReviewCreateRequest(
        @NotNull Long id,
        @NotNull @Positive Integer rating,
        String comment
) {
}
