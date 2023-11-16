package planillaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import planillaservice.entity.PlanillaEntity;
import planillaservice.service.PlanillaService;
import planillaservice.service.SubirPruebaService;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/planilla")
public class PlanillaController {

	@Autowired
	SubirPruebaService subirPruebaService;

	@Autowired
	private PlanillaService planillaService;

	@GetMapping
	public ResponseEntity<ArrayList<PlanillaEntity>> getAll() {
		planillaService.actualizarPlanillas();
		ArrayList<PlanillaEntity> planillas = planillaService.planillas();
		if(planillas.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(planillas);
	}


	@PostMapping
	public void CrearPlanillas(@RequestBody PlanillaEntity planilla){
		planillaService.save(planilla);
	}
	@PostMapping("/prueba")
	public void guardarData(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException {
		subirPruebaService.guardar(file);
		subirPruebaService.leerCsv("Prueba.csv");
	}


}
