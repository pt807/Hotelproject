package com.ptbh.kyungsunghotel.board;


import com.ptbh.kyungsunghotel.member.Member;
import com.ptbh.kyungsunghotel.member.SessionConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {
    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/board/list")
    public String showList(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        if (loginMember != null) {
            model.addAttribute("member", loginMember);
        }

        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "boards/list";
    }
}
