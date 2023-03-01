package corespringsecurity.corespringsecurity.controller.user;

import corespringsecurity.corespringsecurity.domain.Account;
import corespringsecurity.corespringsecurity.domain.dto.AccountDTO;
import corespringsecurity.corespringsecurity.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    @Autowired
    private final AccountService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value="/mypage")
    public String myPage() throws Exception {

        return "user/mypage";
    }


    @GetMapping("/users")
    public String createUser() {
        return "user/login/register";
    }

    @PostMapping("/users")
    public String createUser(AccountDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Account user = modelMapper.map(userDTO, Account.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/";
    }

}
