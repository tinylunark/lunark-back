package com.lunark.lunark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Entity
@Data
public class PropertyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Property property;
    @Lob
    @JsonIgnore
    byte[] imageData;
    @Lob
    @JsonIgnore
    ImageIcon image;
    String mimeType;
}
