package com.ptbh.kyungsunghotel.member;

import com.ptbh.kyungsunghotel.board.Board;
import com.ptbh.kyungsunghotel.reseve.Reserve;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        request.getSession(true);
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

    // 회원 정보 조회
    @GetMapping("/member/info")
    public String showMemberInfo(@SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = false) Member member, Model model) {
        List<Reserve> reserves = memberRepository.findByLoginId(member.getLoginId()).orElse(null).getReserves();
        List<Board> boards = memberRepository.findByLoginId(member.getLoginId()).orElse(null).getBoards();
        boards.sort((o1, o2) -> o2.getBoardNo() - o1.getBoardNo());

        model.addAttribute("reserves", reserves);
        model.addAttribute("boards", boards);
        model.addAttribute("member", member);
        return "members/memberInfo";
    }

    // 회원 정보 수정
    @GetMapping("/member/update")
    public String showMemberUpdateForm(@SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = false) Member member, Model model) {
        UpdateForm updateForm = new UpdateForm();
        updateForm.setName(member.getName());
        updateForm.setEmail(member.getEmail());
        updateForm.setTelephone(member.getTelephone());
        model.addAttribute("updateForm", updateForm);
        return "members/memberUpdateForm";
    }

    // 매개변수 순서를 지키지 않으면 @Validated 검증을 받을 수 없음!!
    // @Validated, BindingResult 를 순서대로 선언
    @PostMapping("/member/update")
    public String updateMember(@Validated UpdateForm updateForm,
                               BindingResult bindingResult,
                               @SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = false) Member member) {
        if (bindingResult.hasErrors()) {
            return "members/memberUpdateForm";
        }

        member.setName(updateForm.getName());
        member.setEmail(updateForm.getEmail());
        member.setTelephone(updateForm.getTelephone());
        memberRepository.save(member);
        return "redirect:/member/info";
    }

    @GetMapping("/member/changePassword")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("passwordForm", new PasswordForm());
        return "members/changePasswordForm";
    }

    // 매개변수 순서를 지키지 않으면 @Validated 검증을 받을 수 없음!!
    // @Validated, BindingResult 를 순서대로 선언
    @PostMapping("/member/changePassword")
    public String changePassword(@Validated PasswordForm passwordForm,
                                 BindingResult bindingResult,
                                 @SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = false) Member member
    ) {
        if (bindingResult.hasErrors()) {
            return "members/changePasswordForm";
        }

        if (!passwordForm.getOldPassword().equals(member.getPassword())) {
            bindingResult.reject("incorrectPassword", "기존 비밀번호가 일치하지 않습니다.");
            return "members/changePasswordForm";
        }

        if (!passwordForm.getNewPassword().equals(passwordForm.getCheckNewPassword())) {
            bindingResult.reject("differentNewPassword", "새 비밀번호가 일치하지 않습니다.");
            return "members/changePasswordForm";
        }

        member.setPassword(passwordForm.getNewPassword());
        memberRepository.save(member);
        return "redirect:/";
    }

    // 회원 탈퇴
    @GetMapping("/member/withdraw")
    public String showWithdraw(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/withdraw";
    }

    @PostMapping("/member/withdraw")
    public String withdraw(@Validated LoginForm loginForm,
                           BindingResult bindingResult,
                           @SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = false) Member member) {
        if (bindingResult.hasErrors()) {
            System.out.println("################ has errors");
            return "members/withdraw";
        }

        if (!member.getLoginId().equals(loginForm.getLoginId())) {
            System.out.println("################ loginIdFail");
            bindingResult.reject("loginIdFail", "아이디가 일치하지 않습니다.");
            return "members/withdraw";
        }

        if (!member.getPassword().equals(loginForm.getPassword())) {
            System.out.println("################ passwordFail");
            bindingResult.reject("passwordFail", "비밀번호가 일치하지 않습니다.");
            return "members/withdraw";
        }

        memberRepository.delete(member);
        return "redirect:/member/logout";
    }

}
