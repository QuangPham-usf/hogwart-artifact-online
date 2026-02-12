package edu.usf.cs.hogwart_artifact_online.artifact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepo extends JpaRepository<Artifact, String> {
}
