package com.ptbh.kyungsunghotel.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/member/login")
    public String showLogin(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }

    @PostMapping("/member/login")
    public String login(@Validated LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/login";
        }
        Member member = memberRepository.findByLoginId(loginForm.getLoginId())
                .filter(m -> m.getPassword().equals(loginForm.getPassword()))
                .orElse(null);
        if (member == null) {
            bindingResult.reject("loginFail", "로그인 정보가 올바르지 않습니다.");
            return "members/login";
        }
        return "redirect:/";
    }
}
