package com.mayab.desarrollo.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mayab.desarrollo.crud.entities.Usuario;
import com.mayab.desarrollo.crud.services.UsuarioCRUDService;


@Controller
public class UsuarioController {
    @Autowired
    public UsuarioCRUDService usuarioCRUDService;

    @RequestMapping("/test")
    public String testService(Model modelo){
        modelo.addAttribute("mensaje", "Este es el mensaje!");
        return "myTemplate";
    }

    @GetMapping("/users")
    public String getUsers(Model modelo){
        modelo.addAttribute("users",usuarioCRUDService.listarTodos());
        return "index";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model modelo) {
        Boolean resultado = usuarioCRUDService.borrarUsuario(id);
        System.out.println(resultado ? "Se borro el usuario con id " + id : "No se pudo borrar el usuario");
        return  "redirect:/users" ;
    }
    
    @GetMapping("/adduser")
    public String crearForm(Model model) {
        model.addAttribute("user", new Usuario()); 
        return "add-user"; 
    }

    @PostMapping("/adduser")
    public String crearUsuario(@ModelAttribute("user") Usuario user) {
        Usuario usuarioCreado = usuarioCRUDService.crearUsuario(user);
        System.out.println(usuarioCreado);
        return "redirect:/users"; 
    }

    @GetMapping("/update/{id}")
    public String updateForm(Model model, @PathVariable("id") Integer id) {
        Usuario user = usuarioCRUDService.encontrarByID(id);
        model.addAttribute("user", user);
        return "update-user"; 
    }

    @PostMapping("/update")
    public String updateUsuario(@ModelAttribute("user") Usuario user ) {
        Usuario usuarioModificado = usuarioCRUDService.actualizarUsuario(user);
        System.out.println(usuarioModificado);
        return "redirect:/users"; 
    }

     




}
