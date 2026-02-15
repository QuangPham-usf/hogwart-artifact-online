package edu.usf.cs.hogwart_artifact_online.artifact;

import edu.usf.cs.hogwart_artifact_online.Wizard.Wizard;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
@Entity
public class Artifact implements Serializable {
    @Id
    private String id;

    private String name;

    private String desciption;

    private String imageUrl;

    public Artifact() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @ManyToOne
    @JoinColumn(name = "wiz_id")// foreign key, put above the owner where it takes it id as key and put into wiz_id

    private Wizard owner;

    public Wizard getOwner() {
        return owner;
    }

    public void setOwner(Wizard owner) {
        this.owner = owner;
    }
}
