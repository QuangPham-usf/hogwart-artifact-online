package edu.usf.cs.hogwart_artifact_online.Wizard.converter;

import edu.usf.cs.hogwart_artifact_online.Wizard.Wizard;
import edu.usf.cs.hogwart_artifact_online.Wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToWizard implements Converter<WizardDto, Wizard> {
    @Override
    public Wizard convert(WizardDto source) {
        Wizard q1 = new Wizard();
        q1.setName(source.name());
        q1.setId(source.id());

        return q1;
    }
}
