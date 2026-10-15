package com.example.GameVault.controller;

import com.example.GameVault.Model.Juego;
import com.example.GameVault.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class GameController {
    @Autowired
    private JuegoService juegoService;

    private static long idCounter = 1;
    private static final String  UPLOAD_DIR="src/main/resources/static/uploads/";


    @GetMapping("/fragments")
    public String fragments(){
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String juegos(Model model){
        model.addAttribute("juegos", this.juegoService.listarTodos());
        return "juegos";
    }

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario(Model model){
        model.addAttribute("juego", new Juego());
        return "formulario";
    }

    @PostMapping("/juegos")
    public String guardarJuego(@RequestParam("titulo") String titulo,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam("precio") Double precio,
                               @RequestParam("categoria") String categoria,
                               @RequestParam("portada") MultipartFile portada) {
        Juego juego = new Juego();
        juego.setTitulo(titulo);
        juego.setDescripcion(descripcion);
        juego.setPrecio(precio);
        juego.setCategoria(categoria);
        this.juegoService.guardarJuego(juego, portada);
        //Redirigimos a la lista de juegos (Patrón PRG - Post/Redirect/GET)
        return "redirect:/juegos";
    }

    @PostMapping("/juegos/eliminar/{id}")
    public String eliminarJuego(@PathVariable Long id){
        this.juegoService.eliminarJuego(id);

        return "redirect:/juegos";
    }

    @GetMapping("/juegos/editar/{id}")
    public String actualizarJuego(@PathVariable Long id, Model model){
        Juego juego = this.juegoService.buscarPorId(id);

        model.addAttribute("juego", juego);

        return "formulario";
    }

    @PostMapping("/juegos/editar/{id}")
    public String guardarJuego(@PathVariable Long id,
                               @RequestParam("titulo") String titulo,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam("precio") Double precio,
                               @RequestParam("categoria") String categoria,
                               @RequestParam(value="portada", required = false) MultipartFile portada) {

        Juego juego = this.juegoService.buscarPorId(id); //Faltan validaciones
        juego.setTitulo(titulo);
        juego.setDescripcion(descripcion);
        juego.setPrecio(precio);
        juego.setCategoria(categoria);
        this.juegoService.guardarJuego(juego, portada);
        //Redirigimos a la lista de juegos (Patrón PRG - Post/Redirect/GET)
        return "redirect:/juegos";
    }

}
