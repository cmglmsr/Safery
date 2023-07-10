package app.safery.controllers;

import app.safery.models.Member;
import app.safery.services.MemberService;
import app.safery.services.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class RootController {

    private final MemberService memberService;
    private final SessionService sessionService;

    public RootController(MemberService memberService, SessionService sessionService) {
        this.memberService = memberService;
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public List<Member> welcomeMessage() {
        return memberService.findAll();
    }

    @GetMapping("/profile")
    public Member getProfile() throws Exception {
        return (Member) sessionService.getCurrentUser();
    }

    @PostMapping("/")
    public void addMember(@RequestBody Member member) {
        System.out.println(member);
        memberService.save(member);
    }
}
