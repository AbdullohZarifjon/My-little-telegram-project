package uz.pdp.telegram.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.telegram.entity.Messages;
import uz.pdp.telegram.entity.Users;
import uz.pdp.telegram.repo.MessageRepo;
import uz.pdp.telegram.repo.UsersRepo;
import uz.pdp.telegram.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static uz.pdp.telegram.controllers.MyController.getString;

@Controller
@RequestMapping("/main2")
@RequiredArgsConstructor
public class Main2Controller {
    private final UsersRepo usersRepo;
    private final MessageRepo messageRepo;

    @GetMapping
    public String main2() {
        return "/login";
    }

    @PostMapping()
    public String forSidebarButton(@RequestParam("toUserId") String userId, HttpSession session, Model model) {
        Object object = session.getAttribute("user");
        if (object == null) {
            return "/login";
        }
        Users user = (Users) object;
        int toUserId = Integer.parseInt(Objects.requireNonNullElse(userId, "0"));
        Users toUser = usersRepo.findById(toUserId).orElse(null);
        System.out.println(new Date());

        return getString(model, toUserId, user, toUser, messageRepo);
    }
}
