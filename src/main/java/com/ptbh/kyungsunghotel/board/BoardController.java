package com.ptbh.kyungsunghotel.board;


import com.ptbh.kyungsunghotel.member.Member;
import com.ptbh.kyungsunghotel.member.SessionConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import java.util.List;

@Controller
public class BoardController {
    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/board/list")
    public String showList(Model model) {
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
    public String post(@Validated PostForm postForm, BindingResult bindingResult,
                       @SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = false) Member member) {
        if (bindingResult.hasErrors()) {
            return "/boards/post";
        }
        Board board = new Board();
        board.setWriter(member.getLoginId());
        board.setTitle(postForm.getTitle());
        board.setContent(postForm.getContent());
        board.setCreateTime(LocalDateTime.now());
        boardRepository.save(board);
        return "redirect:/board/list";
    }

}


