package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.View;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String welcome(){
        return "HomePage";
    }


}
