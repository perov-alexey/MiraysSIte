package com.mirays.entities;

import javax.persistence.*;
import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stage stage = (Stage) o;

        if (id != null ? !id.equals(stage.id) : stage.id != null) return false;
        if (stageName != stage.stageName) return false;
        return Arrays.equals(image, stage.image);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (stageName != null ? stageName.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
