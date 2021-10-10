package com.ptbh.kyungsunghotel.boards;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BoardsController {
    private final BoardRepository boardRepository;

    public BoardsController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/board/list")
    public String showlist(Model model) {
        List<Board> listForms = boardRepository.findAll();
        model.addAttribute("listForm",listForms);
        return "boards/list";
    }
}
