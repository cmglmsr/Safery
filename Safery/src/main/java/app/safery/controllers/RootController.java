package app.safery.controllers;

import app.safery.models.Member;
import app.safery.services.MemberService;
import app.safery.services.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*")
public class RootController {

    private final MemberService memberService;
    private final SessionService sessionService;

    public RootController(MemberService memberService, SessionService sessionService) {
        this.memberService = memberService;
        this.sessionService = sessionService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/")
    public List<Member> welcomeMessage() {
        return memberService.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/profile")
    public Member getProfile() throws Exception {
        return (Member) sessionService.getCurrentUser();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/")
    public void addMember(@RequestBody Member member) {
        System.out.println(member);
        memberService.save(member);
    }
}
