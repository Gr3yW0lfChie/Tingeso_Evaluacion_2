package estudianteservice.controllers;

import estudianteservice.entities.AlumnoEntity;
import estudianteservice.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;


	@GetMapping
	public ResponseEntity<List<AlumnoEntity>> getAll() {
		List<AlumnoEntity> alumnos = alumnoService.obtenerAlumnos();
		if(alumnos.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(alumnos);
	}

	@GetMapping("/{rut}")
	public ResponseEntity<AlumnoEntity> getById(@PathVariable("rut") String rut) {
		AlumnoEntity alumno = alumnoService.obtenerAlumnoPorRut(rut);
		if(alumno == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(alumno);
	}

	@PostMapping()
	public ResponseEntity<AlumnoEntity> save(@RequestBody AlumnoEntity alumno) {
		AlumnoEntity nuevoAlumno = alumnoService.crearAlumno(alumno);
		return ResponseEntity.ok(nuevoAlumno);
	}
}
