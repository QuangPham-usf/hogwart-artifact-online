package edu.usf.cs.hogwart_artifact_online.Wizard;

public class WizardNotFoundException extends RuntimeException{
    public WizardNotFoundException(String Id) {
        super("Could not find artifact with Id " + Id);
    }


}
