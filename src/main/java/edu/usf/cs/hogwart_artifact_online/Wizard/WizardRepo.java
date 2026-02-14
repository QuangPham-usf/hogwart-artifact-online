package edu.usf.cs.hogwart_artifact_online.Wizard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //optional
public interface WizardRepo extends JpaRepository<Wizard, String> {

}
