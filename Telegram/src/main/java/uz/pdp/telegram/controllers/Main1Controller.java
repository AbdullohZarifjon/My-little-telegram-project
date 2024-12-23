package uz.pdp.telegram.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.telegram.entity.Users;
import uz.pdp.telegram.repo.MessageRepo;
import uz.pdp.telegram.repo.UsersRepo;

import java.util.List;


@Controller
@RequestMapping("/main1")
@RequiredArgsConstructor
public class Main1Controller {
    private final UsersRepo usersRepo;

    @GetMapping
    public String main1(HttpSession session, Model model) {
        return "/login";
    }

    @PostMapping
    public String main1Search(@RequestParam("userName") String userName, Model model) {
        List<Users> usersList = usersRepo.findByUsernameOrFirstname(userName);
        model.addAttribute("usersListForSidebar", usersList);
        return "/main1";
    }
}
