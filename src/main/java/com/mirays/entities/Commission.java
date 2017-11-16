package com.mirays.entities;

import javax.persistence.*;

@Entity
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //TODO In future use User here
    private String owner;
    @OneToOne
    private Stage stage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
