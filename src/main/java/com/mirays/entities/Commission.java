package com.mirays.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //TODO In future use User here
    private String owner;
    @OneToOne
    private Stage currentStage;
    @OneToMany
    private List<Stage> stages;

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

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commission that = (Commission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (currentStage != null ? !currentStage.equals(that.currentStage) : that.currentStage != null) return false;
        return stages != null ? stages.equals(that.stages) : that.stages == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (currentStage != null ? currentStage.hashCode() : 0);
        result = 31 * result + (stages != null ? stages.hashCode() : 0);
        return result;
    }
}
