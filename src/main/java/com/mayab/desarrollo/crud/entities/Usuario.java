package com.mayab.desarrollo.crud.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table; 
@Entity
@Table(name="Usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_sequence", allocationSize = 1) 
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	
	public Usuario(int id, String nombre, String password, String email) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.email = email;
	}
	public Usuario() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < password.length(); i++){
			s += "*";
		}
		return this != null ? String.format("[USUARIO %d nombre: %s, email: %s, password: %s] ", id,nombre,email,s) : "No existe el usuario";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {return true;}
		if (obj == null || getClass() != obj.getClass()) {return false;}
		Usuario objeto = (Usuario) obj;
		return this.id == objeto.id && this.nombre.equals(objeto.nombre) && this.email.equals(objeto.email) && this.password.equals(objeto.password);
	}


}
