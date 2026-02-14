package edu.usf.cs.hogwart_artifact_online.Wizard;

import edu.usf.cs.hogwart_artifact_online.Wizard.dto.WizardDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional // business logic handle, if it logic fail, back to it last state and everything not changed, without it, we will lose data
public class WizardService {
    private final WizardRepo wizardRepo;

    public WizardService(WizardRepo wizardRepo) {
        this.wizardRepo = wizardRepo;
    }

    public Wizard findById(String Id) {
        Wizard wizard = wizardRepo.findById(Id).orElseThrow(() -> new WizardNotFoundException(Id));
        return wizard;
    }

    public List<Wizard> findAll() {
        return wizardRepo.findAll();
    }

    public Wizard save(Wizard wizard) {
        return wizardRepo.save(wizard);
    }
}
