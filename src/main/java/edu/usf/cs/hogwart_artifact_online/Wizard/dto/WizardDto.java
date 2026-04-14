package edu.usf.cs.hogwart_artifact_online.Wizard.dto;

import jakarta.validation.constraints.NotEmpty;

public record WizardDto(

        Integer id,

        @NotEmpty(message = "Name empty")
        String name,

        Integer numberofartifact) {
}
