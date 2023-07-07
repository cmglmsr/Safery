package app.safery.services;

import app.safery.models.Member;
import app.safery.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        ArrayList<Member> ls = new ArrayList<>();
        memberRepository.findAll().forEach(ls::add);
        return ls;
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public void delete(Member member) {
        memberRepository.delete(member);
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }


}
