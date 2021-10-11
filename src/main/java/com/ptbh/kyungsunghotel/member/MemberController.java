package com.ptbh.kyungsunghotel.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String login(@Validated LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request) {
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

        HttpSession session = request.getSession();
        session.setAttribute(SessionConstants.LOGIN_MEMBER, member);

        return "redirect:/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionConstants.LOGIN_MEMBER);
        return "redirect:/";
    }

    @GetMapping("/member/join")
    public String showJoin(Model model) {
        model.addAttribute("joinForm", new JoinForm());
        return "members/join";
    }

    @PostMapping("/member/join")
    public String join(@Validated JoinForm joinForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/join";
        }

        Member findMember = memberRepository.findByLoginId(joinForm.getLoginId()).orElse(null);

        if (findMember != null) {
            bindingResult.reject("duplication err", "아이디 중복");
            return "members/join";
        }

        Member member = new Member();
        member.setLoginId(joinForm.getLoginId());
        member.setPassword(joinForm.getPassword());
        member.setName(joinForm.getName());
        member.setEmail(joinForm.getEmail());
        member.setTelephone(joinForm.getTelephone());
        memberRepository.save(member);
        return "redirect:/member/login";
    }

    @PostMapping("/member/join/checkId")
    @ResponseBody
    public boolean checkId(@RequestParam("id") String id) {
        boolean isDuplicate;
        Member member = memberRepository.findByLoginId(id).orElse(null);
        if (member == null) {
            isDuplicate = false;
        } else {
            isDuplicate = true;
        }
        return isDuplicate;
    }
}
