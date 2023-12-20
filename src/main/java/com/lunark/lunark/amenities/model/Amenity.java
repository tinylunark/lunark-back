package com.lunark.lunark.amenities.model;

import com.lunark.lunark.properties.model.Property;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.awt.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql
        = "UPDATE amenity "
        + "SET deleted = true "
        + "WHERE id = ?")
@Where(clause = "deleted = false")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Invalid Name: Should not be blank")
    private String name;

    //Material design icon name
    private String icon;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted = false;

    public Amenity(Long id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return this.name;
    }
}