package com.mayab.desarrollo.crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mayab.desarrollo.crud.dao.UsuarioRepository;
import com.mayab.desarrollo.crud.entities.Usuario;

@Service
public class UsuarioServiceImp implements UsuarioCRUDService{
    @Autowired
    private  UsuarioRepository usuarioRepo;

    @Override
    public Usuario encontrarByID(int id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public boolean borrarUsuario(int id) {
        try {
            usuarioRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Usuario crearUsuario(Usuario user) {
        String nombreUsuario = user.getNombre();
        String emailUsuario = user.getEmail();
        String contrasenaUsuario = user.getPassword();
        
        if(validarUsuario(nombreUsuario, emailUsuario).isEmpty()){;
            user.setPassword(contrasenaUsuario.isBlank() || contrasenaUsuario == null ? 
                            nombreUsuario.substring(0, 3) + 
                            emailUsuario.substring(0, 3) 
                            :
                            contrasenaUsuario);
        
            //Comentar si no se esta usando MySQL o SQLite
            usuarioRepo.actualizarConteo(obtenerHueco());

            //Comentar si no se esta usando MariaDB
            //usuarioRepo.actualizarConteoMariaDB(obtenerHueco());
            user = usuarioRepo.save(user);
        }else {
            user = null;
        }
        
        return user;
    }

    @Override
    public List<Optional<Usuario>> validarUsuario(String nombre, String email) {
        return usuarioRepo.findByUsernameOrEmail(nombre, email);
    }

    @Override   
    public int obtenerHueco() {
        int hueco = 0;
        List<Usuario> usuarios = listarTodos();
        int maxId = usuarios.isEmpty() ?  0 : usuarios.getLast().getId();
        if(maxId != 0 && usuarios.size() != maxId){
            for (Usuario user : usuarios) {
                hueco++;
                if(user.getId() != hueco){
                    break;
                }
            }
            return hueco;
        }
        return (maxId + 1);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuarioModificar) {
        
        int id = usuarioModificar.getId();
        Usuario usuarioOriginal = encontrarByID(id);
        
        String nombreUsuario = usuarioModificar.getNombre().isBlank() ? usuarioOriginal.getNombre() : usuarioModificar.getNombre();
        String emailUsuario = usuarioModificar.getEmail().isBlank() ?  usuarioOriginal.getEmail() : usuarioModificar.getEmail();
        String contrasenaUsuario = usuarioModificar.getPassword().isBlank() ? usuarioOriginal.getPassword() : usuarioModificar.getPassword();
        
        List<Optional<Usuario>> listaValidaciones = validarUsuario(nombreUsuario,emailUsuario);

        if (listaValidaciones.size() > 1){
            for (Optional<Usuario> user : validarUsuario(nombreUsuario, emailUsuario)) {
                if(user.isPresent()){
                    if(!user.get().equals(usuarioOriginal)){
                        if(!user.get().getNombre().equals(nombreUsuario) && !user.get().getEmail().equals(emailUsuario)){
                            usuarioRepo.update(nombreUsuario, emailUsuario, contrasenaUsuario, id);
                        }
                    }

                }
            }
        }else{
            if(listaValidaciones.isEmpty()){
                usuarioRepo.update(nombreUsuario, emailUsuario, contrasenaUsuario, id);
            }else{
                if(listaValidaciones.getFirst().get().equals(usuarioOriginal)){
                    usuarioRepo.update(nombreUsuario, emailUsuario, contrasenaUsuario, id);
                }
            }
        }
        return encontrarByID(id);
    }
}
