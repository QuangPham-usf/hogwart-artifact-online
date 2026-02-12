package edu.usf.cs.hogwart_artifact_online.artifact.B2B;


import edu.usf.cs.hogwart_artifact_online.artifact.Artifact;
import edu.usf.cs.hogwart_artifact_online.artifact.dto.ArtifactDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtDtotoOriginal implements Converter<ArtifactDto, Artifact> {

    @Override
    public Artifact convert(ArtifactDto source) {
        Artifact q1 = new Artifact();// a record dont have getId/ just Id();
        q1.setId(source.id());
        q1.setDesciption(source.desciption());
        q1.setImageUrl(source.imageUrl());
        q1.setName(source.name());
        return q1;
    }
}
