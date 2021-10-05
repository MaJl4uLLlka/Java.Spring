package javaspring.springproject.controller;

import javaspring.springproject.forms.BookForm;
import javaspring.springproject.model.Book;
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
public class BookController {
    private static List<Book> books = new ArrayList<>();

    static {
        books.add( new Book("Full Stack Development with JHipster", "Deepu K Sasidharan," +
                "Sendil Kumar N"));
        books.add(new Book("Pro Spring Security", "Carlo Scarioni, Massimo Nardone"));
    }

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @GetMapping(value = {"/","/index"})
    public ModelAndView index(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("/index was called");
        return modelAndView;
    }

    @GetMapping(value = {"/allbooks"})
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booklist");
        model.addAttribute("books", books);
        log.info("/allbooks was called");
        return modelAndView; }

    @GetMapping(value = {"/addbook"})
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addbook");
        BookForm bookForm = new BookForm();
        model.addAttribute("bookform", bookForm);
        log.info("/addbook was called");
        return modelAndView;
    }

    // @PostMapping("/addbook") //GetMapping("/")
    @PostMapping(value = {"/addbook"})
    public ModelAndView savePerson(Model model, //
    @ModelAttribute("bookform") BookForm bookForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booklist");
        String title = bookForm.getTitle();
        String author = bookForm.getAuthor();
        if (title != null && title.length() > 0 //
        && author != null && author.length() > 0)
        {
            Book newBook = new Book(title, author);
            books.add(newBook);
            model.addAttribute("books",books);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addbook");
        return modelAndView;
    }

    @GetMapping(value = {"/delete"})
    public ModelAndView deletePerson(Model model, @ModelAttribute("bookform") BookForm bookForm)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deleting");
        model.addAttribute("books", books);

        //TODO logic need here
        return modelAndView;
    }

    @GetMapping(value = {"/edit"})
    public ModelAndView editPerson(Model model, @ModelAttribute("boookform") BookForm bookForm)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editing");

        //TODO logic
        return modelAndView;
    }
}
