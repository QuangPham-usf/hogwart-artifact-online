package edu.usf.cs.hogwart_artifact_online.User;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Integer id) {
        super("Could not find artifact with Id " + id);
    }
}
