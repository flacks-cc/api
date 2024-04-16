package com.tutorial.crud.security.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.repository.UsuariosRepository;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	UsuariosRepository usuariosRepository;

	public List<Usuario> findAll() {
		return usuariosRepository.findAll();
	}

	public Optional<Usuario> findById(Long idUsuario) {
		return usuariosRepository.findById(idUsuario);
	}

	public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
		return usuariosRepository.findByNombreUsuario(nombreUsuario);
	}

	public boolean existsByNombreUsuario(String nombreUsuario) {
		return usuariosRepository.existsByNombreUsuario(nombreUsuario);
	}
	
	   public long countByRolNombre(RolNombre rolNombre) {
	        return usuariosRepository.countByRolesNombre(rolNombre);
	    }

	public boolean existsByEmail(String email) {
		return usuariosRepository.existsByEmail(email);
	}

	public boolean existsByTelefonoAndIdUsuarioNot(String telefono, Long idUsuario) {
		return usuariosRepository.existsByTelefonoAndIdUsuarioNot(telefono, idUsuario);
	}

	public boolean existsByNombreUsuarioAndIdUsuarioNot(String nombreUsuario, Long idUsuario) {
		return usuariosRepository.existsByNombreUsuarioAndIdUsuarioNot(nombreUsuario, idUsuario);
	}

	public boolean existsByEmailAndIdUsuarioNot(String email, Long idUsuario) {
		return usuariosRepository.existsByEmailAndIdUsuarioNot(email, idUsuario);
	}

	public boolean existsByTelefono(String telefono) {
		return usuariosRepository.existsByTelefono(telefono);
	}

	public Optional<Usuario> findByNombreUsuario(String nombreUsuario) {
		return usuariosRepository.findByNombreUsuario(nombreUsuario);
	}

	public Usuario getByEmail(String email) {
		return usuariosRepository.findByEmail(email);
	}

	public Optional<Usuario> getByNombreUsuarioOrEmail(String nombreUsuario, String email) {
		return usuariosRepository.findByNombreUsuarioOrEmail(nombreUsuario, email);
	}

	public void save(Usuario usuario) {
		usuariosRepository.save(usuario);
	}

	public void deleteById(Long idUsuario) {
		usuariosRepository.deleteById(idUsuario);
	}

	public boolean existsById(Long idUsuario) {
		return usuariosRepository.existsById(idUsuario);
	}
}
