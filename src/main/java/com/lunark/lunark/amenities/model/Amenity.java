package com.lunark.lunark.amenities.model;

import com.lunark.lunark.properties.model.Property;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Invalid Name: Should not be blank")
    private String name;
    //TODO: Store images
    @Transient
    private Image icon;

    @Override
    public String toString() {
        return this.name;
    }
}