package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
       return "login";
    }

    /*@GetMapping("/logout")
    public RedirectView logout(){
        return new RedirectView("");
    }*/
}
