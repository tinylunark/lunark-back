package com.lunark.lunark.properties.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class Address{
    @NotBlank
    String street;
    @NotBlank
    String city;
    @NotBlank
    String country;
}
