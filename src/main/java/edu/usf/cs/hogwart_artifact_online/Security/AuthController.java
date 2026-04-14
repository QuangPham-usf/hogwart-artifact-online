package edu.usf.cs.hogwart_artifact_online.Security;

import edu.usf.cs.hogwart_artifact_online.system.Result;
import edu.usf.cs.hogwart_artifact_online.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    // Authentication object is automatically injected by Spring Security after successful authentication
    public Result getLoginInfo(Authentication authentication) {// obj authen - represent a authenticated user
        LOGGER.debug("Authentication object: {}", authentication.getName());
        return new Result(true, StatusCode.SUCCESS,"User info and JWT", authService.createLoginInfo(authentication));
    }
}
/*
Authentication = {
    principal: Users (the user object)      ← User data
    credentials: password                   ← Password (not really used after login)
    authorities: [Role_admin, Role_user]    ← Roles/permissions
    authenticated: true                     ← Login status
}

PART 1 (Automatic - Spring does this):
User sends: username + password
    ↓
Spring Security authenticates ✅

PART 2 (NOT automatic - YOU control this):
Client calls: POST /api/v1/users/login
    ↓
AuthController.getLoginInfo() is called
    ↓
AuthService.createLoginInfo() is called
    ↓
JwtProvider.createToken() is called
    ↓
Return JWT token

 */