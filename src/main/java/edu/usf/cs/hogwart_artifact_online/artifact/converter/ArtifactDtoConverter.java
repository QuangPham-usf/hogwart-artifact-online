package edu.usf.cs.hogwart_artifact_online.artifact.converter;

import edu.usf.cs.hogwart_artifact_online.Wizard.converter.WizardDtoconverter;
import edu.usf.cs.hogwart_artifact_online.artifact.Artifact;
import edu.usf.cs.hogwart_artifact_online.artifact.dto.ArtifactDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactDtoConverter implements Converter<Artifact, ArtifactDto>{
    private final WizardDtoconverter wizardDtoconverter;

    public ArtifactDtoConverter(WizardDtoconverter wizardDtoconverter) {
        this.wizardDtoconverter = wizardDtoconverter;
    }

    @Override
    public ArtifactDto convert(Artifact source) {
        ArtifactDto q1 = new ArtifactDto(source.getId(),
                                            source.getName(),
                                            source.getDesciption(),
                                            source.getImageUrl(),
                source.getOwner() != null ? wizardDtoconverter.convert(source.getOwner()) : null);
        return q1;
    }
}
