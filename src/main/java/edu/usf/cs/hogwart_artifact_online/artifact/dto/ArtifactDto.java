package edu.usf.cs.hogwart_artifact_online.artifact.dto;

import edu.usf.cs.hogwart_artifact_online.Wizard.dto.WizardDto;
import jakarta.validation.constraints.NotEmpty; //  not empty, @valid is  from jak.validation

public record ArtifactDto(

        String id,  // they are auto private and final so dont add private

        @NotEmpty(message = "name is required")
         String name,

         @NotEmpty(message = "desc is required")
         String desciption,

         @NotEmpty(message = "url is required")
         String imageUrl,

         WizardDto owner) {
}
