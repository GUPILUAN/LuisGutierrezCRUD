package com.mayab.desarrollo.crud.services;

import java.util.List;
import java.util.Optional;

import com.mayab.desarrollo.crud.entities.Usuario;


public interface UsuarioCRUDService {

    public Usuario encontrarByID(int id);
    public List<Usuario> listarTodos();
    public boolean borrarUsuario(int id);
    public Usuario crearUsuario(Usuario user);
    public List<Optional<Usuario>> validarUsuario(String nombre, String email);
    public int obtenerHueco();
    public Usuario actualizarUsuario(Usuario usuarioModificar) ;
    
}
