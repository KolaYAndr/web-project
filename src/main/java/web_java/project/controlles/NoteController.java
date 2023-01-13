package web_java.project.controlles;

import models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repos.NoteRepo;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class NoteController {

    @Autowired
    private NoteRepo noteRepo;

    @GetMapping("/notes")
    public String notes(Model model) {
        Iterable<Note> notes = noteRepo.findAll();
        model.addAttribute("notes", notes);
        return "notes";
    }

    @GetMapping("/notes/add")
    public String notesAdd(Model model) {
        return "notes-add";
    }

    @PostMapping("/notes/add")
    public String add(@RequestParam String title, @RequestParam String text, @RequestParam String authorName, Model model) {
        Note note = new Note(title, text, authorName);
        noteRepo.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/notes/{id}")
    public String noteDetail(@PathVariable(value = "id") Long id, Model model) {
        if (!noteRepo.existsById(id)) {
            return "redirect:/notes";
        }

        Optional<Note> note = noteRepo.findById(id);
        ArrayList<Note> res = new ArrayList<>();
        note.ifPresent(res::add);
        model.addAttribute("post", res);
        return "notes-details";
    }

    @GetMapping("/notes/{id}/edit")
    public String noteEdit(@PathVariable(value = "id") Long id, Model model) {
        if (!noteRepo.existsById(id)) {
            return "redirect:/notes";
        }

        Optional<Note> note = noteRepo.findById(id);
        ArrayList<Note> res = new ArrayList<>();
        note.ifPresent(res::add);
        model.addAttribute("post", res);
        return "notes-edit";
    }

    @PostMapping("/notes/{id}/edit")
    public String edit(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String text, @RequestParam String authorName, Model model){
        Note note = noteRepo.findById(id).orElseThrow();

        note.setTitle(title);
        note.setText(text);
        note.setAuthorName(authorName);
        noteRepo.save(note);

        return "redirect:/notes";
    }

    @PostMapping("/notes/{id}/remove")
    public String remove(@PathVariable(value = "id") Long id, Model model){
        Note note = noteRepo.findById(id).orElseThrow();
        noteRepo.delete(note);
        return "redirect:/notes";
    }
}
