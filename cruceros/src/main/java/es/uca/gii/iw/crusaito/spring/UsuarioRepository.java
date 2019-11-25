package es.uca.gii.iw.crusaito.spring;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.uca.gii.iw.crusaito.clases.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findById(int id);
	Usuario findByUsername(String username);
	Usuario findByEmailIgnoreCase(String email);
	List<Usuario> findByDni(String dni);
}