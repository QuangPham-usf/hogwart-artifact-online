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
/*
Step 1: Read JSON
Step 2: Create Artifact object
Step 3: Fill fields:
  ├─ id = "1"
  ├─ name = "Wand"
  └─ owner = new Wizard()
      Step 3a: Fill Wizard fields:
        ├─ id = 5
        ├─ name = "Harry"
        └─ artifacts = [Artifact, Artifact, ...]
            Step 3a-i: For each Artifact:
              ├─ id = ...
              ├─ name = ...
              └─ owner = new Wizard()  ← BACK TO WIZARD AGAIN!
                  Step 3a-i-α: Fill Wizard fields again...
                    └─ artifacts = [...]  ← BACK TO ARTIFACTS AGAIN!

❌ INFINITE LOOP! 🔄
 */