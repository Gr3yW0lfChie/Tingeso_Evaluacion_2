package planillaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import planillaservice.entity.PlanillaEntity;
import planillaservice.service.PlanillaService;
import planillaservice.service.SubirPrueba;

@RestController
@RequestMapping("/planilla")
public class PlanillaController {

	@Autowired
	SubirPrueba subirPrueba;

	@Autowired
	private PlanillaService planillaService;

	@GetMapping
	public String mainSubir() {
		return "subir";
	}
	@PostMapping("/examen")
	public String uploadExamen(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		subirPrueba.guardar(file);
		boolean correcto = subirPrueba.leerCsvExamen(file.getOriginalFilename());
		if (correcto)
		{
			redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
		}
		else
		{
			redirectAttributes.addFlashAttribute("mensaje", "Verifique el archivo que esta subiendo");
		}
		return "redirect:/"; //Cambiar
	}

	@PostMapping()
	public ResponseEntity<String> save(@RequestBody PlanillaEntity planilla) {
		planillaService.save(planilla);
		return ResponseEntity.ok("Planilla guardada");
	}
}
