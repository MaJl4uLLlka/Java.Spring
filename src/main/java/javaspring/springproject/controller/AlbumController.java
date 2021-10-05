package javaspring.springproject.controller;

import javaspring.springproject.forms.AlbumForm;
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

    }

}
