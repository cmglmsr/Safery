package app.safery.controllers;

import app.safery.models.Member;
import app.safery.services.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class RootController {

    private final MemberService memberService;

    public RootController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> welcomeMessage() {
        return memberService.findAll();
    }

    @PostMapping()
    public void addMember(@RequestBody Member member) {
        memberService.save(member);
    }

}
