package web_java.project.controlles;

import models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import repos.NoteRepo;

@Controller
public class MainController {

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/auth")
    public String authorise(Model model) {
        return "auth";
    }

}