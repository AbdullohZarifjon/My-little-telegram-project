package uz.pdp.telegram.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.telegram.entity.Users;
import uz.pdp.telegram.repo.MessageRepo;
import uz.pdp.telegram.repo.UsersRepo;

import java.util.List;


@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UsersRepo usersRepo;
    private final MessageRepo messageRepo;

    @GetMapping()
    public String login() {
        return "/login";
    }

    @PostMapping()
    public String login(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Users users = usersRepo.findByUsernameAndPassword(username, password);
        if (users == null) {
            model.addAttribute("error", "Invalid username or password");
            return "/login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", users);
        List<Users> usersForSidebarButton = messageRepo.findUniqueToUsersByFromUserId(users.getId());
        model.addAttribute("usersListForSidebar", usersForSidebarButton);
        return "/main1";
    }
}
