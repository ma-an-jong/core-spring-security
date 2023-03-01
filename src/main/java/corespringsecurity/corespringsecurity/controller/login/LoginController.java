package corespringsecurity.corespringsecurity.controller.login;

import corespringsecurity.corespringsecurity.authentication.details.user.AccountContext;
import corespringsecurity.corespringsecurity.domain.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {

    private final LogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        @RequestParam(value = "error",required = false) String error,
                        @RequestParam(value = "exception",required = false) String exception,
                        Model model) {
        HttpSession session = request.getSession();
        SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if(securityContext != null) return "redirect:/";
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "user/login/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            log.info("LoginController {}" , authentication);
            logoutHandler.logout(request,response,authentication);
        }
        return "redirect:/login";
    }

    @GetMapping("/denied")
    public String denied(@RequestParam(value="exception" , required = false) String exception,
                         Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountContext principal = (AccountContext)authentication.getPrincipal();
        log.info("principal ={}",principal);
        log.info("getDetails ={}",authentication.getDetails());

        model.addAttribute("username",principal.getUsername());
        model.addAttribute("exception",exception);

        return "/user/login/denied";
    }
}
