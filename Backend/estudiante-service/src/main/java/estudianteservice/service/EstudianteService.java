package estudianteservice.service;

import estudianteservice.entity.EstudianteEntity;
import estudianteservice.model.CuotaModel;
import estudianteservice.model.PlanillaModel;
import estudianteservice.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstudianteService {

	@Autowired
	private EstudianteRepository estudianteRepository;

	@Autowired
	private RestTemplate restTemplate;

	//----------------------------------------------------------------------------------------------------------
	//Busqueda
	public ArrayList<EstudianteEntity> obtenerAlumnos() {
		return (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
	}

	public EstudianteEntity obtenerAlumnoPorRut(String rut){
		return estudianteRepository.findByRut(rut);
	}

	//----------------------------------------------------------------------------------------------------------
	//Crear
	public EstudianteEntity crearAlumno(EstudianteEntity alumno){
		EstudianteEntity nuevoAlumno = estudianteRepository.save(alumno);
		crearPlanilla(alumno.getRut(), alumno.getApellidos(), alumno.getNombres(), new PlanillaModel());
		return nuevoAlumno;
	}

	//----------------------------------------------------------------------------------------------------------
	//Eliminar
	/*
	public void eliminarAlumno(String rut){
		try{
			alumnoRepository.deleteByRut(rut);
		}catch (Exception ignored){
		}
	}
	*/

	//----------------------------------------------------------------------------------------------------------
	//Modificar
	public EstudianteEntity actualizarAlumno(String rut, EstudianteEntity alumnoActualizado){
		EstudianteEntity alumno = estudianteRepository.findByRut(rut);
		alumno.setRut(alumnoActualizado.getRut());
		alumno.setApellidos(alumnoActualizado.getApellidos());
		alumno.setNombres(alumnoActualizado.getNombres());
		alumno.setFechaNacimiento(alumnoActualizado.getFechaNacimiento());
		alumno.setTipoColegio(alumnoActualizado.getTipoColegio());
		alumno.setNombreColegio(alumnoActualizado.getNombreColegio());
		alumno.setFechaEgreso(alumnoActualizado.getFechaEgreso());
		return estudianteRepository.save(alumno);
	}

	//----------------------------------------------------------------------------------------------------------
	//Obtener descuento por tipo de colegio (Municipal, Subvencionado, Particular)
	public Integer obtenerDescuentoPorTipoColegio(String alumno){
		return switch (alumno) {
			case "Municipal" -> 20;
			case "Subvencionado" -> 10;
			case "Particular" -> 0;
			default -> -1;
		};
	}

	//----------------------------------------------------------------------------------------------------------
	//Obtener descuento por tiempo de salida del colegio

	public Integer obtenerDescuentoPorTiempoSalidaColegio(LocalDate fechaEgreso){
		int descuento;
		LocalDate fechaActual = LocalDate.of(2024, 1, 1);

		Period periodo = Period.between(fechaEgreso, fechaActual);

		Integer diferencia = periodo.getYears();

		if(diferencia < 1){
			descuento = 15;
		} else if (diferencia < 3){
			descuento = 8;
		} else if (diferencia < 5){
			descuento = 4;
		} else {
			descuento = 0;
		}

		return descuento;
	}

	//----------------------------------------------------------------------------------------------------------
	//Obtener cantidad de cuotas que puede tener el alumno
	public Integer obtenerCantidadCuotas(String alumno){
		return switch (alumno) {
			case "Municipal" -> 10;
			case "Subvencionado" -> 7;
			case "Particular" -> 4;
			default -> -1;
		};
	}

	public String crearCuotasEstudiantes(String rutEstudiante, int cantidadCuotas) {
		EstudianteEntity estudiante = estudianteRepository.findByRut(rutEstudiante);
		int precioBase = 1500000;

		if (estudiante != null) {

			if (cantidadCuotas == 1) {
				precioBase = precioBase / 2;
			} else {
				int descuento = obtenerDescuentoPorTipoColegio(estudiante.getTipoColegio()) +
								obtenerDescuentoPorTiempoSalidaColegio(estudiante.getFechaEgreso());
				precioBase = precioBase - (precioBase * descuento / 100);
			}

			// Construye la URL de manera segura con UriComponentsBuilder
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://cuota-service/cuota/crearCuotas")
					.queryParam("cantidadCuotas", cantidadCuotas)
					.queryParam("rutEstudiante", rutEstudiante)
					.queryParam("precioBase", precioBase);
			String url = builder.toUriString();

			restTemplate.postForEntity(url, null, String.class);
		}
		return "Cuotas creadas";
	}

	public List<CuotaModel> obtenerCuotas(String rutEstudiante) {
		List<CuotaModel> cuotas = restTemplate.getForObject("http://cuota-service/porRut/" + rutEstudiante, List.class);
		return cuotas;
	}

	public void crearPlanilla(String rutAlumno, String apellidoAlumno, String nombreAlumno, PlanillaModel planilla) {
		planilla.setRutAlumno(rutAlumno);
		planilla.setNombreAlumno(nombreAlumno + " " + apellidoAlumno);
		HttpEntity<PlanillaModel> request = new HttpEntity<PlanillaModel>(planilla);
		restTemplate.postForObject("http://planilla-service/planilla", request, PlanillaModel.class);
	}
}
