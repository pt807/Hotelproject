package com.ptbh.kyungsunghotel.reserve;

import com.ptbh.kyungsunghotel.member.Member;
import com.ptbh.kyungsunghotel.member.SessionConstants;
import com.ptbh.kyungsunghotel.room.Room;
import com.ptbh.kyungsunghotel.room.RoomRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReserveController {
    private final RoomRepository roomRepository;
    private final ReserveRepository reserveRepository;

    public ReserveController(RoomRepository roomRepository, ReserveRepository reserveRepository) {
        this.roomRepository = roomRepository;
        this.reserveRepository = reserveRepository;
    }

    @GetMapping("/reserve")
    public String showReserve(Model model) {
        return "/reserves/reserve";
    }

    @PostMapping("/reserve")
    public String reserve(@RequestParam("checkIn") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,
                          @RequestParam("checkOut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut,
                          @RequestParam("roomNo") String roomNo,
                          @SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = false) Member member) {
        for (long day = 0; day < ChronoUnit.DAYS.between(checkIn, checkOut); day++) {
            Reserve reserve = new Reserve();
            reserve.setDate(checkIn.plusDays(day));
            reserve.setMember(member);
            reserve.setRoom(roomRepository.findById(roomNo).orElse(null));
            reserveRepository.save(reserve);
        }

        return "redirect:/member/info";
    }

    @GetMapping("/reserve/searchRoom")
    @ResponseBody
    public List<Room> searchRoom(@RequestParam("checkIn") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,
                                 @RequestParam("checkOut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut) {
        List<Room> rooms = roomRepository.findAll();
        List<Room> reserveRooms = new ArrayList<>();
        List<Reserve> reserves = reserveRepository.findAllByDateBetween(checkIn, checkOut.minusDays(1));
        for (Reserve reserve : reserves) {
            reserveRooms.add(reserve.getRoom());
        }
        rooms.removeAll(reserveRooms);
        return rooms;
    }
}
