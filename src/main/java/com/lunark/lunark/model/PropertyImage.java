package com.lunark.lunark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;
import org.springframework.data.jpa.repository.Query;

import javax.swing.*;

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
    @Lob
    @JdbcType(VarbinaryJdbcType.class)
    @JsonIgnore
    ImageIcon image;
    String mimeType;
}
