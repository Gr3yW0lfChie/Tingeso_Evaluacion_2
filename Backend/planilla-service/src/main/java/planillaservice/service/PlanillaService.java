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

	public void save(PlanillaEntity planilla) {
		planilla.setExamenesRendidos(0);
		planilla.setPromedioExamenes(0);
		planilla.setTotalArancel(0);
		planilla.setTipoPago("n/s");
		planilla.setCuotasPactadas(0);
		planilla.setCuotasPagadas(0);
		planilla.setMontoPagado(0);
		planilla.setFechaUltimoPago(null);
		planilla.setSaldoPorPagar(0);
		planilla.setCuotasImpagas(0);

		planillaRepository.save(planilla);
	}

	public void update(String rut, PlanillaEntity planilla){
		PlanillaEntity planillaActual = planillaRepository.findByRutAlumno(rut);
		planillaActual.setExamenesRendidos(planilla.getExamenesRendidos());
		planillaActual.setPromedioExamenes(planilla.getPromedioExamenes());
		planillaActual.setTotalArancel(planilla.getTotalArancel());
		planillaActual.setTipoPago(planilla.getTipoPago());
		planillaActual.setCuotasPactadas(planilla.getCuotasPactadas());
		planillaActual.setCuotasPagadas(planilla.getCuotasPagadas());
		planillaActual.setMontoPagado(planilla.getMontoPagado());
		planillaActual.setFechaUltimoPago(planilla.getFechaUltimoPago());
		planillaActual.setSaldoPorPagar(planilla.getSaldoPorPagar());
		planillaActual.setCuotasImpagas(planilla.getCuotasImpagas());
		planillaRepository.save(planillaActual);
	}

	
}
