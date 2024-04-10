package com.tutorial.crud.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.security.dto.JwtDto;
import com.tutorial.crud.security.dto.LoginUsuario;
import com.tutorial.crud.security.dto.NuevoUsuario;
import com.tutorial.crud.security.dto.UsuarioActualizado;
import com.tutorial.crud.security.entity.Rol;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.entity.UsuarioPrincipal;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.jwt.JwtProvider;
import com.tutorial.crud.security.service.RolService;
import com.tutorial.crud.security.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RolService rolService;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/nuevo")
	public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
	    try {
	        if (bindingResult.hasErrors())
	            throw new IllegalArgumentException("Campos incorrectos o email invalido");

	        if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
	            throw new IllegalArgumentException("Este usuario ya existe");

	        if (usuarioService.existsByEmail(nuevoUsuario.getEmail()))
	            throw new IllegalArgumentException("Este email ya existe");

	        if (usuarioService.existsByTelefono(nuevoUsuario.getTelefono()))
	            throw new IllegalArgumentException("Este teléfono ya existe");

	        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellidoPaterno(),
	                nuevoUsuario.getApellidoMaterno(), nuevoUsuario.getTelefono(), nuevoUsuario.getNombreUsuario(),
	                nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));

	        Set<Rol> roles = new HashSet<>();
	        roles.add(rolService.getByNombre(RolNombre.ROLE_USER).orElseThrow(() -> new RuntimeException("No se encontró el rol de usuario")));

	        if (nuevoUsuario.getRoles().contains("admin"))
	            roles.add(rolService.getByNombre(RolNombre.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("No se encontró el rol de administrador")));
	        
	        if (nuevoUsuario.getRoles().contains("empleado"))
	            roles.add(rolService.getByNombre(RolNombre.ROLE_EMPLEADO).orElseThrow(() -> new RuntimeException("No se encontró el rol de empleado")));

	        usuario.setRoles(roles);
	        usuarioService.save(usuario);

	        return new ResponseEntity(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
	    } catch (IllegalArgumentException e) {
	        return new ResponseEntity(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        return new ResponseEntity(new Mensaje("Ocurrió un error al procesar la solicitud"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new ResponseEntity(new Mensaje("Campos incorrectos"), HttpStatus.BAD_REQUEST);
		Authentication authentication = null;
		try {
			Usuario usuario = usuarioService
					.getByNombreUsuarioOrEmail(loginUsuario.getNombreUsuario(), loginUsuario.getEmail())
					.orElseThrow(() -> new UsernameNotFoundException(
							"No se ha encontrado un usuario con ese nombre de usuario o correo electrónico"));
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(usuario.getNombreUsuario(), loginUsuario.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity(new Mensaje("Nombre de usuario o correo electrónico y/o contraseña incorrectos"),
					HttpStatus.BAD_REQUEST);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UsuarioPrincipal userDetails = (UsuarioPrincipal) authentication.getPrincipal();
		String jwt = jwtProvider.generateToken(authentication);
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getEmail(),
				userDetails.getAuthorities());
		return new ResponseEntity(jwtDto, HttpStatus.OK);
	}

	@PutMapping("/usuarios/{idUsuario}")
	public ResponseEntity<?> actualizarUsuario(@PathVariable Long idUsuario,
			@Valid @RequestBody UsuarioActualizado usuarioActualizado, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Mensaje("Campos incorrectos o email inválido"), HttpStatus.BAD_REQUEST);
		}

		Optional<Usuario> usuarioExistente = usuarioService.findById(idUsuario);
		if (!usuarioExistente.isPresent()) {
			return new ResponseEntity<>(new Mensaje("Usuario no encontrado"), HttpStatus.NOT_FOUND);
		}

		Usuario usuario = usuarioExistente.get();

		// Verificar si el correo, contraseña y teléfono son iguales a los actuales
		if (usuarioActualizado.getEmail().equals(usuario.getEmail())
				&& usuarioActualizado.getPassword().equals(usuario.getPassword())
				&& usuarioActualizado.getTelefono().equals(usuario.getTelefono())) {
			// Si son iguales, no se realiza ninguna validación adicional
		} else {
			// Verificar si ya existen en la base de datos
			if (usuarioService.existsByTelefonoAndIdUsuarioNot(usuarioActualizado.getTelefono(), idUsuario)) {
				return new ResponseEntity<>(new Mensaje("Este número de teléfono ya está en uso"),
						HttpStatus.BAD_REQUEST);
			}

			if (usuarioService.existsByEmailAndIdUsuarioNot(usuarioActualizado.getEmail(), idUsuario)) {
				return new ResponseEntity<>(new Mensaje("Este correo electrónico ya está en uso"),
						HttpStatus.BAD_REQUEST);
			}
		}

		if (usuarioService.existsByNombreUsuarioAndIdUsuarioNot(usuarioActualizado.getNombreUsuario(), idUsuario)) {
			return new ResponseEntity<>(new Mensaje("Este nombre de usuario ya está en uso"), HttpStatus.BAD_REQUEST);
		}

		usuario.setNombre(usuarioActualizado.getNombre());
		usuario.setApellidoPaterno(usuarioActualizado.getApellidoPaterno());
		usuario.setApellidoMaterno(usuarioActualizado.getApellidoMaterno());
		usuario.setTelefono(usuarioActualizado.getTelefono());
		usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
		usuario.setEmail(usuarioActualizado.getEmail());
		usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));

		// Actualiza los roles del usuario si es necesario
		Set<Rol> roles = new HashSet<>();
		if (usuarioActualizado.getRoles().contains("admin")) {
			roles.add(rolService.getByNombre(RolNombre.ROLE_ADMIN).get());
		}
		if (usuarioActualizado.getRoles().contains("empleado")) {
			roles.add(rolService.getByNombre(RolNombre.ROLE_EMPLEADO).get());
		}
		usuario.setRoles(roles);

		usuarioService.save(usuario);

		return new ResponseEntity<>(new Mensaje("Usuario actualizado"), HttpStatus.OK);
	}

	// Método para borrar un usuario
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/usuarios/{idUsuario}")
	public ResponseEntity<?> borrarUsuario(@PathVariable("idUsuario") Long idUsuario) {
		// Verificar si el usuario con el ID proporcionado existe
		if (!usuarioService.existsById(idUsuario)) {
			return new ResponseEntity<>(new Mensaje("No se encontró un usuario con el ID proporcionado"),
					HttpStatus.NOT_FOUND);
		}

		// Borrar el usuario de la base de datos
		usuarioService.deleteById(idUsuario);

		return new ResponseEntity<>(new Mensaje("Usuario eliminado correctamente"), HttpStatus.OK);
	}

	// Método para listar todos los usuarios
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		List<Usuario> usuarios = usuarioService.findAll();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}

	// Método para obtener un usuario por su ID
	@GetMapping("/usuarios/{idUsuario}")
	public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable("idUsuario") Long idUsuario) {
		// Verificar si el usuario con el ID proporcionado existe
		Optional<Usuario> usuarioOptional = usuarioService.findById(idUsuario);
		if (!usuarioOptional.isPresent()) {
			return new ResponseEntity<>(new Mensaje("No se encontró un usuario con el ID proporcionado"),
					HttpStatus.NOT_FOUND);
		}

		// Devolver el usuario encontrado
		Usuario usuario = usuarioOptional.get();
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
}
