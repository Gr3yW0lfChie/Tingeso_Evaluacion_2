package estudianteservice.controller;

import estudianteservice.entity.EstudianteEntity;
import estudianteservice.model.CuotaModel;
import estudianteservice.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
	public ResponseEntity<String> crearCuotas(@PathVariable("rut") String rut, @RequestBody Map<String, Object> requestBody) {
		if (estudianteService.obtenerAlumnoPorRut(rut) == null) {
			return ResponseEntity.notFound().build();
		}
		if (requestBody.containsKey("cantidadCuotas")) {
			try {
				Integer cantidadCuotas = Integer.valueOf(requestBody.get("cantidadCuotas").toString());
				String creacion = estudianteService.crearCuotasEstudiantes(rut, cantidadCuotas);
				return ResponseEntity.ok("Cuotas creadas exitosamente");
			} catch (NumberFormatException e) {
				return ResponseEntity.badRequest().body("El campo 'cantidadCuotas' debe ser un número entero");
			}
		} else {
			return ResponseEntity.badRequest().body("El campo 'cantidadCuotas' es obligatorio");
		}
	}


	@GetMapping("/cuotas/{rut}")
	public ResponseEntity<List<CuotaModel>> obtenerCuotas(@PathVariable("rut") String rut) {
		EstudianteEntity estudiante = estudianteService.obtenerAlumnoPorRut(rut);
		if(estudiante == null)
			return ResponseEntity.notFound().build();
		List<CuotaModel> cuotas = estudianteService.obtenerCuotas(rut);
		return ResponseEntity.ok(cuotas);
	}
}
