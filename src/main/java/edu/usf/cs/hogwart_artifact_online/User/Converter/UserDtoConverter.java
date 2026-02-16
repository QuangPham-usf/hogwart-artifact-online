package edu.usf.cs.hogwart_artifact_online.User.Converter;

import edu.usf.cs.hogwart_artifact_online.User.UserDto.UserDto;
import edu.usf.cs.hogwart_artifact_online.User.Users;
import org.apache.catalina.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Converter<Users, UserDto> {

    @Override
    public UserDto convert(Users source) {
        UserDto q1 = new UserDto(source.getId(), source.getUserName(),source.getEnabled(),source.getRoles());
        return q1;
    }


}
