package com.mirays.entities;

import javax.persistence.*;

@Entity
public class Stage {

    public Stage() {
    }

    public Stage(StageName stageName, byte[] image) {
        this.stageName = stageName;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StageName stageName;

    private byte[] image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StageName getStageName() {
        return stageName;
    }

    public void setStageName(StageName stageName) {
        this.stageName = stageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
