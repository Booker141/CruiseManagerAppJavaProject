package es.uca.gii.iw.crusaito.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.gii.iw.crusaito.clases.Servicio;
import es.uca.gii.iw.crusaito.clases.Usuario;
import es.uca.gii.iw.crusaito.repositorios.ServicioRepository;
import es.uca.gii.iw.crusaito.repositorios.UsuarioRepository;

@Service
public class ServicioService {

	private ServicioRepository repo;
	private UsuarioRepository userRepo;
	
	@Autowired
	public ServicioService(ServicioRepository repo, UsuarioRepository userRepo) {
		this.repo = repo;
		this.userRepo = userRepo;
	}
	
	public Servicio findById(long id) {
		return this.repo.findById(id);
	}
	
	public Servicio save(Servicio servicio) {
		return this.repo.save(servicio);
	}
	
	public void delete(Servicio servicio) {
		this.repo.delete(servicio);
	}
	
	public List<Servicio> load(){
		return this.repo.findAll();
	}
	
	public void addServicioToUsusario(Servicio servicio, Usuario usuario) {
		Servicio service = this.repo.findById(servicio.getId());
		service.addUsuario(usuario);
		this.repo.save(service);

		Usuario user = this.userRepo.findById(usuario.getId());
		user.addServicio(service);
		this.userRepo.save(user);
	}
}
