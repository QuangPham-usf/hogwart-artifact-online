package edu.usf.cs.hogwart_artifact_online.Security;

import edu.usf.cs.hogwart_artifact_online.User.Converter.UserDtoConverter;
import edu.usf.cs.hogwart_artifact_online.User.MyUserPrincipal;
import edu.usf.cs.hogwart_artifact_online.User.UserDto.UserDto;
import edu.usf.cs.hogwart_artifact_online.User.Users;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    // JwtProvider removed for now - will implement JWT later
    private final UserDtoConverter userDtoConverter;
    private final JwtProvider jwtProvider;
    public AuthService(UserDtoConverter userDtoConverter, JwtProvider jwtProvider) {
        this.userDtoConverter = userDtoConverter;
        this.jwtProvider = jwtProvider;
    }

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        // Create user info
        MyUserPrincipal userPrincipal = (MyUserPrincipal) authentication.getPrincipal(); // cast to MyUserPrincipal to access user data
        Users user = userPrincipal.getUsers();
        UserDto userDto = userDtoConverter.convert(user);

        String token = jwtProvider.createToken(authentication);

        Map<String, Object> loginResultMap = new HashMap<>();
        loginResultMap.put("userInfo", userDto);
        loginResultMap.put("token", token);

        return loginResultMap;
    }
}
