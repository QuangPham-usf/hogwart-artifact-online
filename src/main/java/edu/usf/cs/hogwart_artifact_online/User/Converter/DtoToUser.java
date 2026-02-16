package edu.usf.cs.hogwart_artifact_online.User.Converter;

import edu.usf.cs.hogwart_artifact_online.User.UserDto.UserDto;
import edu.usf.cs.hogwart_artifact_online.User.Users;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToUser implements Converter<UserDto, Users> {
    @Override
    public Users convert(UserDto source) {
        Users q1 = new Users( source.userName(),source.id(),null, source.enabled(), source.roles());
        return q1;
    }
}
