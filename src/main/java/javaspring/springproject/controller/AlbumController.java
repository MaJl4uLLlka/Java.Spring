package javaspring.springproject.controller;

import javaspring.springproject.forms.AlbumForm;
import javaspring.springproject.forms.DeletingForm;
import javaspring.springproject.model.Album;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping
public class AlbumController {
    private static List<Album> albums = new ArrayList<>();

    static {
        albums.add(new Album("Sosa Muzik", "Платина"));
        albums.add(new Album("FREERIO", "OG Buda"));
        albums.add(new Album("2004", "Скриптонит"));
    }

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @GetMapping (value = {"/", "index"})
    public ModelAndView index(Model model)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("/index was called");
        return modelAndView;
    }

    @GetMapping(value = {"/allalbums"})
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        model.addAttribute("albums", albums);
        log.info("/allalbums was called");
        return modelAndView; }

    @GetMapping(value = {"/addalbum"})
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addalbum");
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("albumform", albumForm);
        log.info("/addalbum was called");
        return modelAndView;
    }

    // @PostMapping("/addbook") //GetMapping("/")
    @PostMapping(value = {"/addalbum"})
    public ModelAndView savePerson(Model model, //
                                   @ModelAttribute("albumform") AlbumForm albumForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        String title = albumForm.getTitle();
        String author = albumForm.getAuthor();
        if (title != null && title.length() > 0 //
                && author != null && author.length() > 0)
        {
            Album newAlbum = new Album(title, author);
            albums.add(newAlbum);
            model.addAttribute("albums",albums);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addalbum");
        return modelAndView;
    }

    @GetMapping(value = {"/delete"})
    public ModelAndView getAlbum(Model model)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deleting");
        DeletingForm deletingForm = new DeletingForm();
        model.addAttribute("deletingform", deletingForm);
        model.addAttribute("albums", albums);

        return modelAndView;
    }

    @PostMapping(value = {"/delete"})
    public ModelAndView removeAlbum(Model model,
                                    @ModelAttribute("deletingform") DeletingForm deletingform)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        String deletingTitle = deletingform.getTitle();

        //TODO: not correct
        //удаляются все с таким названием

        albums.removeIf(album -> album.getTitle().equals(deletingTitle));

        model.addAttribute("albums",albums);
        return modelAndView;
    }

    @GetMapping(value = {"/edit"})
    public ModelAndView editPerson(Model model, @ModelAttribute("albumform") AlbumForm albumForm)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editing");

        //TODO logic
        return modelAndView;
    }

}
