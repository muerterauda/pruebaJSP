package com.example.controller;

import com.example.models.Cancion;
import com.example.models.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class restController {
    @Autowired
    private CancionRepository repo;

    @PostMapping("/crear")
    public ModelAndView gre(@RequestParam String nombre,@RequestParam String autor, Map<String, Object> model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("starter");
        repo.save(new Cancion(nombre, autor, "Nice"));
        model.put("lista", repo.findAll());
        return modelAndView;
    }

    @GetMapping("/redireccion")
    public ModelAndView fre () {
        return new ModelAndView("exito");
    }
}
