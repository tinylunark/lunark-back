package com.lunark.lunark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Property property;
    @Lob
    @JdbcType(VarbinaryJdbcType.class)
    @JsonIgnore
    byte[] imageData;
    String mimeType;
}
