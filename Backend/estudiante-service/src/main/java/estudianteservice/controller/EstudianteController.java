package estudianteservice.controller;

import estudianteservice.entity.EstudianteEntity;
import estudianteservice.model.CuotaEntity;
import estudianteservice.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

	@Autowired
	private EstudianteService estudianteService;


	@GetMapping
	public ResponseEntity<List<EstudianteEntity>> getAll() {
		List<EstudianteEntity> alumnos = estudianteService.obtenerAlumnos();
		if(alumnos.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(alumnos);
	}

	@GetMapping("/{rut}")
	public ResponseEntity<EstudianteEntity> getById(@PathVariable("rut") String rut) {
		EstudianteEntity alumno = estudianteService.obtenerAlumnoPorRut(rut);
		if(alumno == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(alumno);
	}

	@PostMapping()
	public ResponseEntity<EstudianteEntity> save(@RequestBody EstudianteEntity alumno) {
		EstudianteEntity nuevoAlumno = estudianteService.crearAlumno(alumno);
		return ResponseEntity.ok(nuevoAlumno);
	}

	@PostMapping("/crearCuotas/{rut}")
	public ResponseEntity<String> crearCuotas(@PathVariable("rut") String rut, @RequestBody Integer cantidadCuotas){
		if(estudianteService.obtenerAlumnoPorRut(rut) == null)
			return ResponseEntity.notFound().build();
		String creacion = estudianteService.crearCuotasEstudiantes(rut, cantidadCuotas);
		return ResponseEntity.ok(creacion);
	}

	@GetMapping("/cuotas/{rut}")
	public ResponseEntity<List<CuotaEntity>> obtenerCuotas(@PathVariable("rut") String rut) {
		EstudianteEntity estudiante = estudianteService.obtenerAlumnoPorRut(rut);
		if(estudiante == null)
			return ResponseEntity.notFound().build();
		List<CuotaEntity> cuotas = estudianteService.obtenerCuotas(rut);
		return ResponseEntity.ok(cuotas);
	}
}
