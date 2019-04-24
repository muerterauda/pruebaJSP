package com.example.controller;

import com.example.models.Cancion;
import com.example.models.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class mainController {

    @Autowired
    private CancionRepository repo;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView getIndex(Map<String, Object> model) {
        model.put("lista", repo.findAll());
        return new ModelAndView("starter");
    }
}
