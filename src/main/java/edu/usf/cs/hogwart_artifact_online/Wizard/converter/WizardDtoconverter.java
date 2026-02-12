package edu.usf.cs.hogwart_artifact_online.Wizard.converter;


import edu.usf.cs.hogwart_artifact_online.Wizard.Wizard;
import edu.usf.cs.hogwart_artifact_online.Wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Component
public class WizardDtoconverter implements Converter<Wizard, WizardDto>{

    @Override
    public WizardDto convert(Wizard source) {
        WizardDto q1 = new WizardDto(source.getId(), source.getName(), source.getArtifacts().size());
        return q1;
    }
}
