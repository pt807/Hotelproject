package com.ptbh.kyungsunghotel.system;

import com.ptbh.kyungsunghotel.member.Member;
import com.ptbh.kyungsunghotel.member.SessionConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String welcome(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        if (loginMember != null) {
            model.addAttribute("member", loginMember);
        }

        return "welcome";
    }
}
