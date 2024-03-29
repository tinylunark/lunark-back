package com.lunark.lunark.properties.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lunark.lunark.properties.model.Property;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

@Entity
@Data
public class PropertyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Lob
    @JdbcType(VarbinaryJdbcType.class)
    @JsonIgnore
    byte[] imageData;
    String mimeType;
}
