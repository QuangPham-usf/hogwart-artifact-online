package edu.usf.cs.hogwart_artifact_online.Wizard;

import edu.usf.cs.hogwart_artifact_online.artifact.Artifact;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity

// the @ is the jpa instruction, he not do work but leader, the hibernate do the work or worker
public class Wizard implements Serializable {
    @Id// jpa lead, hibernate change it into database
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    // cascade: save mutually, merge : update wizard -> update, mappedby: i dony have the foreignkey
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST ,CascadeType.MERGE})
    private List<Artifact> artifacts = new ArrayList<>(); // should be a list here represent many

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public Wizard() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void addArtifacts(Artifact single_artifact) {
        single_artifact.setOwner(this);
        this.artifacts.add(single_artifact);
    }

    public Integer getNumOfArtifact() {
        return this.artifacts.size();
    }
    public void removeAllArtifact() {
        artifacts.stream().forEach(a1 -> a1.setOwner(null));
        artifacts = null;
    }
}
