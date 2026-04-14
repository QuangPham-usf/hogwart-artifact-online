package edu.usf.cs.hogwart_artifact_online.artifact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepo extends JpaRepository<Artifact, String> {
}
//inside <> is the type of the entity and the type of the primary key, in this case, Artifact and String