package com.mayab.desarrollo.crud.dao;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mayab.desarrollo.crud.entities.Usuario;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT * FROM Usuarios u WHERE u.nombre = :usuario OR u.email = :email", nativeQuery = true)
    List<Optional<Usuario>> findByUsernameOrEmail(@Param("usuario") String usuario, @Param("email") String email);

    // Si se esta usando SQLite o SQLite se debe usar actualizarConteo
    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE usuario_sequence RESTART WITH :hueco", nativeQuery = true)
    void actualizarConteoMariaDB(@Param("hueco") int hueco);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE usuario_sequence SET next_val = :hueco", nativeQuery = true)
    void actualizarConteo(@Param("hueco") int hueco);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Usuarios SET nombre = :nombre, email = :email , password = :password where id = :id", nativeQuery = true)
    void update(@Param("nombre")String nombre, @Param("email")String email,@Param("password") String password ,@Param("id")Integer id);


}