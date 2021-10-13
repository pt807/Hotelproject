package com.ptbh.kyungsunghotel.board;


import com.ptbh.kyungsunghotel.member.Member;
import com.ptbh.kyungsunghotel.member.MemberRepository;
import com.ptbh.kyungsunghotel.member.SessionConstants;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
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

    @GetMapping("/board/post")
    public String showPost(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "/boards/post";
    }

    @PostMapping("/board/post")
    public String post(@Validated PostForm postForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "/boards/post";
        }
        Board board = new Board();
       // board.setWriter(session.getId());
        board.setTitle(postForm.getTitle());
        board.setContent(postForm.getContent());
        board.setCreateTime(LocalDateTime.now());
        boardRepository.save(board);
        return "redirect:/board/list";
    }


}


