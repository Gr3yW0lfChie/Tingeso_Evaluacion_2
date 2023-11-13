package cuotaservice.controller;

import cuotaservice.entity.CuotaEntity;
import cuotaservice.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
			@RequestParam("rut") String rut,
			@RequestParam("precioBase") Integer precioBase) {
		cuotaService.crearCuotas(cantidadCuotas, rut, precioBase);
		return new ResponseEntity<>("Cuotas creadas exitosamente", HttpStatus.OK);
	}

}
