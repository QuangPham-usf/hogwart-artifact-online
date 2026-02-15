package edu.usf.cs.hogwart_artifact_online.Wizard;

import edu.usf.cs.hogwart_artifact_online.Wizard.dto.WizardDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.util.List;

@Service
@Transactional // business logic handle, if it logic fail, back to it last state and everything not changed, without it, we will lose data
public class WizardService {
    private final WizardRepo wizardRepo;

    public WizardService(WizardRepo wizardRepo) {
        this.wizardRepo = wizardRepo;
    }

    public Wizard findById(Integer Id) {
        Wizard wizard = wizardRepo.findById(Id).orElseThrow(() -> new WizardNotFoundException(Id));
        return wizard;
    }

    public List<Wizard> findAll() {
        return wizardRepo.findAll();
    }

    public Wizard save(Wizard wizard) {
        return wizardRepo.save(wizard);
    }

    public Wizard update(Integer Id, Wizard wizard) {
        Wizard wiz = wizardRepo.findById(Id).orElseThrow(() -> new WizardNotFoundException(Id));
        wiz.setName(wizard.getName());
        Wizard wizard1 = wizardRepo.save(wiz);
        return wizard1;
    }
    public void delete(Integer Id) {
        Wizard deleteOne = wizardRepo.findById(Id).orElseThrow(() -> new WizardNotFoundException(Id));
        deleteOne.removeAllArtifact();
        wizardRepo.deleteById(Id);
    }

}
