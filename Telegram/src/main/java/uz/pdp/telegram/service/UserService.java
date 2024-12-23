package uz.pdp.telegram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.telegram.repo.UsersRepo;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepo usersRepo;

    public boolean isUserNameExist(String username) {
        return usersRepo.findByUsername(username).isPresent();
    }

    public boolean isUserPhoneNumExist(String phoneNum) {
        return usersRepo.findByPhoneNumber(phoneNum).isPresent();
    }
}
