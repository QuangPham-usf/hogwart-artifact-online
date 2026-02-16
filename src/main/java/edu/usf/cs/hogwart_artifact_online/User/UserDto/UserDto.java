package edu.usf.cs.hogwart_artifact_online.User.UserDto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        Integer id,

        @NotEmpty(message="name required")
        String userName,

        boolean enabled,

        @NotEmpty(message = "roles required")
        String roles // space separated string

){


}
