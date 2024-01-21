package com.lunark.lunark.properties.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Address{
    @NotNull
    @NotBlank
    @Pattern(message = "The street address can not contain special characters", regexp = "^[^<>%$]*$")
    String street;
    @NotNull
    @NotBlank
    @Pattern(message = "The city can not contain special characters", regexp = "^[^<>%$]*$")
    String city;
    @NotNull
    @NotBlank
    @Pattern(message = "The country can not contain special characters", regexp = "^[^<>%$]*$")
    String country;
}
