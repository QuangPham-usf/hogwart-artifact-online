package edu.usf.cs.hogwart_artifact_online.system;

import edu.usf.cs.hogwart_artifact_online.Wizard.Wizard;
import edu.usf.cs.hogwart_artifact_online.Wizard.WizardRepo;
import edu.usf.cs.hogwart_artifact_online.artifact.Artifact;
import edu.usf.cs.hogwart_artifact_online.artifact.ArtifactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component//only bean can receive bean
public class DBDataInitializer implements CommandLineRunner {// run this method
    // always mark them private final
    private final ArtifactRepo artifactRepo;
    private final WizardRepo wizardRepo;
    public DBDataInitializer(ArtifactRepo artifactRepo, WizardRepo wizardRepo) {
        this.artifactRepo = artifactRepo;
        this.wizardRepo = wizardRepo;
    }
    // jackson transfer object to json format: serialization
    @Override
    public void run(String... args) throws Exception {
        Artifact a4 = new Artifact();
        a4.setId("1250808601744904194");
        a4.setName("Marauder's Map");
        a4.setDesciption("A magical document that reveals all of Hogwarts School of Witchcraft and Wizardry.");
        a4.setImageUrl("ImageUrl");

        Artifact a6 = new Artifact();
        a6.setId("1250808601744904196");
        a6.setName("Sword of Gryffindor");
        a6.setDesciption("A thousand-year-old goblin-made sword that once belonged to Godric Gryffindor.");
        a6.setImageUrl("ImageUrl");

        Artifact a5 = new Artifact();
        a5.setId("1250808601744904195");
        a5.setName("Resurrection Stone");
        a5.setDesciption("One of the Deathly Hallows, said to have the power to recall loved ones from the dead.");
        a5.setImageUrl("ImageUrl");

        Artifact aa = new Artifact();
        aa.setId("1250908601744904888");
        aa.setName("meo cung");
        aa.setDesciption("beo i");
        aa.setImageUrl("bubu");
        artifactRepo.save(aa);

        Wizard wizard = new Wizard();
        wizard.addArtifacts(a5);
        wizard.addArtifacts(a6);
        wizard.setName("qp");

        wizardRepo.save(wizard); // save wizard mean save all artifact
        artifactRepo.save(a4);
    }
}
