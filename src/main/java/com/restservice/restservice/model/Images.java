package com.restservice.restservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="imgname", nullable = false)
    private String imgName;

    @Column(nullable = false)
    private byte[] img;

    public Images(String imgName, byte[] img) {
        this.imgName = imgName;
        this.img = img;
    }
}
