package mk.ukim.finki.deals_n_steals.web.controller;

import mk.ukim.finki.deals_n_steals.config.CustomUsernamePasswordAuthenticationProvider;
import mk.ukim.finki.deals_n_steals.model.ShoppingCart;
import mk.ukim.finki.deals_n_steals.model.enumeration.CartStatus;
import mk.ukim.finki.deals_n_steals.model.enumeration.Role;
import mk.ukim.finki.deals_n_steals.model.exception.InvalidArgumentsException;
import mk.ukim.finki.deals_n_steals.model.exception.PasswordDoNotMatchException;
import mk.ukim.finki.deals_n_steals.model.exception.UserNameExistsException;
import mk.ukim.finki.deals_n_steals.service.AuthService;
import mk.ukim.finki.deals_n_steals.service.ShoppingCartService;
import mk.ukim.finki.deals_n_steals.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;
    private final CustomUsernamePasswordAuthenticationProvider customUsernamePasswordAuthenticationProvider;

    public RegisterController(UserService userService, ShoppingCartService shoppingCartService, AuthService authService, CustomUsernamePasswordAuthenticationProvider customUsernamePasswordAuthenticationProvider) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
        this.customUsernamePasswordAuthenticationProvider = customUsernamePasswordAuthenticationProvider;
    }


    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam Role role,
                           Model model){
        try {
            this.userService.register(username, password, name, surname, role);
        }catch (PasswordDoNotMatchException | InvalidArgumentsException | UserNameExistsException exception){
            return "redirect:/register?error=" + exception.getMessage();
        }
//        if(this.authService.getCurrentUserId() != null) {
//            ShoppingCart shoppingCart = this.shoppingCartService.findByUsernameAndStatus(this.authService.getCurrentUserId(), CartStatus.CREATED);
//            model.addAttribute("size", shoppingCart.getProducts().size());
//        }
//        else model.addAttribute("size", 0);
        return "redirect:/login";
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error",error);
        }
        model.addAttribute("bodyContent","register");
        return "master-details";
    }
}