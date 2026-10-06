package com.example.GameVault.controller;

import com.example.GameVault.Model.Juego;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    private static List<Juego> juegosdb = new ArrayList<>();
    private static long idCounter = 1;
    private static final String  UPLOAD_DIR="src/main/resources/static/uploads/";


    @GetMapping("/fragments")
    public String fragments(){
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String juegos(){
        return "juegos";
    }

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario(){
        return "formulario";
    }
}
