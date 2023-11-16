package planillaservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import planillaservice.entity.PlanillaEntity;
import planillaservice.model.CuotaModel;
import planillaservice.repository.PlanillaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanillaService {
	@Autowired
	private PlanillaRepository planillaRepository;

	@Autowired
	private RestTemplate restTemplate;

	public ArrayList<PlanillaEntity> planillas() {
		return (ArrayList<PlanillaEntity>) planillaRepository.findAll();
	}

	public PlanillaEntity planillaPorRut(String rut) {
		return planillaRepository.findByRutAlumno(rut);
	}

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



	public void actualizarPlanillas() {
		List<PlanillaEntity> planillas = planillas();
		for (PlanillaEntity planilla : planillas) {
			ResponseEntity<List<CuotaModel>> response = restTemplate.exchange(
					"http://cuota-service/cuota/porRut/" + planilla.getRutAlumno(),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<CuotaModel>>() {}
			);
			List<CuotaModel> cuotas = response.getBody();

			planilla.setTotalArancel(calcularTotalArancel(cuotas));
			planilla.setTipoPago(obtenerTipoPago(cuotas));
			planilla.setCuotasPactadas(cantidadCuotas(cuotas));
			planilla.setCuotasPagadas(cuotasPagadas(cuotas));
			planilla.setMontoPagado(montoPagado(cuotas));
			planilla.setFechaUltimoPago(fechaUltimoPago(cuotas));
			planilla.setSaldoPorPagar(saldoPorPagar(cuotas));
			planilla.setCuotasImpagas(cuotasImpagas(cuotas));
			planillaRepository.save(planilla);
		}
	}


	public Integer calcularTotalArancel(List<CuotaModel> cuotas) {
		Integer totalArancel = 0;
		for(CuotaModel cuota : cuotas) {
			totalArancel += cuota.getPrecioAPagar();
		}
		return totalArancel;
	}

	public String obtenerTipoPago(List<CuotaModel> cuotas) {
		String tipoPago = "";
		if (cantidadCuotas(cuotas) == 1) {
			tipoPago = "Contado";
		}else {
			tipoPago = "Cuotas";
		}
		return tipoPago;
	}
	public Integer cantidadCuotas(List<CuotaModel> cuotas) {
		return cuotas.size();
	}

	public Integer cuotasPagadas(List<CuotaModel> cuotas) {
		Integer cuotasPagadas = 0;
		for(CuotaModel cuota : cuotas) {
			if(cuota.getCuotaPagada()) {
				cuotasPagadas++;
			}
		}
		return cuotasPagadas;
	}

	public Integer montoPagado(List<CuotaModel> cuotas) {
		Integer montoPagado = 0;
		for(CuotaModel cuota : cuotas) {
			if(cuota.getCuotaPagada()) {
				montoPagado += cuota.getPrecioAPagar();
			}
		}
		return montoPagado;
	}

	public LocalDate fechaUltimoPago(List<CuotaModel> cuotas) {
		LocalDate fechaUltimoPago = null;
		for(CuotaModel cuota : cuotas) {
			if(cuota.getCuotaPagada()) {
				fechaUltimoPago = cuota.getFechaVencimiento();
			}
		}
		return fechaUltimoPago;
	}

	public Integer saldoPorPagar(List<CuotaModel> cuotas) {
		Integer saldoPorPagar = 0;
		for(CuotaModel cuota : cuotas) {
			if(!cuota.getCuotaPagada()) {
				saldoPorPagar += cuota.getPrecioAPagar();
			}
		}
		return saldoPorPagar;
	}

	public Integer cuotasImpagas(List<CuotaModel> cuotas) {
		Integer cuotasImpagas = 0;
		for(CuotaModel cuota : cuotas) {
			if(!cuota.getCuotaPagada()) {
				if(cuota.getPorcentajeInteres() > 0){
					cuotasImpagas++;
				}
			}
		}
		return cuotasImpagas;
	}


	public Integer calcularPromedioExamen(Integer nuevoPuntaje, String rutAlumno) {
		PlanillaEntity planilla = planillaRepository.findByRutAlumno(rutAlumno);

		if (planilla.getExamenesRendidos() == 0) {
			return nuevoPuntaje;
		} else {
			return (planilla.getPromedioExamenes() * planilla.getExamenesRendidos() + nuevoPuntaje) / (planilla.getExamenesRendidos() + 1);
		}
	}

}
