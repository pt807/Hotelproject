package com.ptbh.kyungsunghotel.reseve;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReserveController {

    @GetMapping("/reserve")
    public String reserve(Model model){
        model.addAttribute("reserve", new Reserve());
        return "/reserves/reserve";
    }
}
