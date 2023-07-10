package app.safery.services;

import app.safery.config.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
@Service
public class SessionService {
    private final JwtUtils jwtUtils;
    private final MemberService memberService;

    public SessionService(JwtUtils jwtUtils, MemberService memberService) {
        this.jwtUtils = jwtUtils;
        this.memberService = memberService;
    }

    public Object getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ArrayList<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        if(authentication.getAuthorities()==null)
            return null;

        String email = authentication.getName();
        String role = authorities.get(0).getAuthority();

        return switch (role) {
            case "ROLE_USER" -> memberService.findByEmail(email);
            default -> null;
        };
    }

    public Long getCurrentUserId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ArrayList<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        if(authentication.getAuthorities()==null)
            return -1L;

        String email = authentication.getName();
        String role = authorities.get(0).getAuthority();

        return switch (role) {
            case "ROLE_USER" -> memberService.findByEmail(email).getId();
            default -> -1L;
        };
    }
}
