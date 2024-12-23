package uz.pdp.telegram.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.telegram.entity.Users;
import uz.pdp.telegram.repo.UsersRepo;
import uz.pdp.telegram.service.UserService;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;
    private final UsersRepo usersRepo;

    @GetMapping()
    public String registerPage() {
        return "/register";
    }

    @PostMapping()
    public String register(HttpServletRequest request, Model model) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        if (firstName == null || lastName == null || username == null || email == null || password == null || phoneNumber == null) {
            return "/register";
        }
        boolean isUserNameExist = userService.isUserNameExist(username);
        boolean isUserPhoneNumExist = userService.isUserPhoneNumExist(phoneNumber);
        if (!isUserNameExist && !isUserPhoneNumExist) {
            Users user = new Users(firstName, lastName, username, email, phoneNumber, password);
            usersRepo.save(user);
            return "/login";
        }
        if (isUserNameExist) {
            model.addAttribute("errorUserName", "username already exist");
        }
        if (isUserPhoneNumExist) {
            model.addAttribute("errorPhoneNum", "phone number already exist");
        }
        return "/register";

    }

}
