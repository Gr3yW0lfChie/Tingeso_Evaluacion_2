package planillaservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planillaservice.entity.PlanillaEntity;
import planillaservice.repository.PlanillaRepository;

import java.time.LocalDate;

@Service
public class PlanillaService {
	@Autowired
	private PlanillaRepository planillaRepository;

	public void guardarNotas(String rut, LocalDate fecha, Integer puntaje){
		PlanillaEntity nuevaPlanillaExamen = new PlanillaEntity();
		nuevaPlanillaExamen.setAlumno(rut);
		nuevaPlanillaExamen.setFechaExamen(fecha);
		nuevaPlanillaExamen.setPuntaje(puntaje);
		planillaRepository.save(nuevaPlanillaExamen);
	}
}
