package cuotaservice.controller;

import cuotaservice.entity.CuotaEntity;
import cuotaservice.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cuota")
public class CuotaController {

	@Autowired
	CuotaService cuotaService;

	@GetMapping
	public ResponseEntity<List<CuotaEntity>> getAll() {
		List<CuotaEntity> cuotas = cuotaService.obtenerCuotas();
		if(cuotas.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(cuotas);
	}

	@GetMapping("/porRut/{rut}")
	public ResponseEntity<List<CuotaEntity>> buscarCuotasPorRut(@PathVariable("rut") String rut) {
		List<CuotaEntity> cuotas = cuotaService.findByRutAlumno(rut);
		if(cuotas.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(cuotas);
	}

	@PostMapping("/crearCuotas")
	public ResponseEntity<String> crearCuotas(
			@RequestParam("cantidadCuotas") Integer cantidadCuotas,
			@RequestParam("rutEstudiante") String rutEstudiante,
			@RequestParam("precioBase") Integer precioBase) {
		cuotaService.crearCuotas(cantidadCuotas, rutEstudiante, precioBase);
		return new ResponseEntity<>("Cuotas creadas exitosamente", HttpStatus.OK);
	}

	@PostMapping("/pagarCuota/{id}")
	public ResponseEntity<String> pagarCuota(@PathVariable("id") Long id) {
		cuotaService.pagarCuota(id);
		return new ResponseEntity<>("Cuota pagada exitosamente", HttpStatus.OK);
	}


	@PostMapping("/interesCuotas")
	public ResponseEntity<String> interesCuotas(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaCambiada) {
		cuotaService.modificarCuotasVencidas(fechaCambiada);
		return new ResponseEntity<>("Cuotas actualizadas exitosamente", HttpStatus.OK);
	}



	@PostMapping("/descuentoCuotas")
	public ResponseEntity<String> descuentoCuotas(
			@RequestParam("rut") String rut,
			@RequestParam("fechaExamen")LocalDate fechaExamen,
			@RequestParam("promedio") Integer promedio) {
		cuotaService.actualizarDescuentoCuotas(rut, fechaExamen, promedio);
		return new ResponseEntity<>("Cuotas actualizadas exitosamente", HttpStatus.OK);
	}

}
