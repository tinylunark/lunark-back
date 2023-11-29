package com.lunark.lunark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Amenity {
    private Long id;
    private String name;
    private Image icon;

    @Override
    public String toString() {
        return this.name;
    }
}