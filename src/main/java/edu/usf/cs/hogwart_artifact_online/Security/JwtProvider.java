package edu.usf.cs.hogwart_artifact_online.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private JwtEncoder jwtEncoder;

    public JwtProvider(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiresIn = 2; // 2 hours
/*
Authentication.getAuthorities(): Nó không tự chuyển đổi hay tạo mới gì cả, nó chỉ đơn giản là trả collection of grAU
MyUserPrincipal.getAuthorities(): Nó là một cái "Converter". Nó chủ động tạo mới (new SimpleGrantedAuthority) các tấm thẻ quyền hạn từ thuộc tính roles của Entity User.
-> khác nhau
 */
        String authorities = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(" "));

        //Making claims in payload
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn * 3600))
                .subject(authentication.getName())
                .claim("authorities", authorities) // Custom to change scope(authority container indefault) -> authority
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
/*
getAuthor() return a collection of GrantedAuthority("role")
-> stream() transfer each to map which convert each to string of role name
-> now we have like ["Role_admin", "Role_user", "Role_viewer"] -> just join with " "
    -> "Role_admin Role_user Role_viewer"

 */
