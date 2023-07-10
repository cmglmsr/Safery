package app.safery.controllers;

import app.safery.config.JwtUtils;
import app.safery.controllers.dto.AuthenticationRequest;
import app.safery.models.Member;
import app.safery.repositories.MemberRepository;
import app.safery.repositories.UserRepository;
import app.safery.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final MemberService memberService;

    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        Member usr;
        try {
            usr = memberService.findByEmail(request.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("[-] Error extracting user with email: " + request.getEmail());
        }
        if(usr==null) {
            return ResponseEntity.status(400).body("[-] Could not find user with email: " + request.getEmail());
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userRepository.findUserByEmail(request.getEmail());
        if(user!=null) {
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("[-] Error during authentication.");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody Member person) {
        if(Objects.equals(person.getRole(), "ROLE_USER")) {
            memberService.save(person);
        }
        else {
            return ResponseEntity.badRequest().body("[-] User could not be registered.");
        }

        return ResponseEntity.ok("User successfully registered.");
    }
}
