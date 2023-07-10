package app.safery.repositories;

import app.safery.models.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public class UserRepository {

    private final MemberRepository memberRepository;

    public UserRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public UserDetails findUserByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        User user = new User(member.getEmail(), member.getPassword(), Collections.singleton(new SimpleGrantedAuthority(member.getRole())));
        return user;
    }
}
