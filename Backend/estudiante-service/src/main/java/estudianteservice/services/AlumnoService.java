package estudianteservice.services;

import estudianteservice.entities.AlumnoEntity;
import estudianteservice.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@Service
public class AlumnoService {

	@Autowired
	private AlumnoRepository alumnoRepository;

	//----------------------------------------------------------------------------------------------------------
	//Busqueda
	public ArrayList<AlumnoEntity> obtenerAlumnos() {
		return (ArrayList<AlumnoEntity>) alumnoRepository.findAll();
	}

	public AlumnoEntity obtenerAlumnoPorRut(String rut){
		return alumnoRepository.findByRut(rut);
	}

	//----------------------------------------------------------------------------------------------------------
	//Crear
	public AlumnoEntity crearAlumno(AlumnoEntity alumno){
		return alumnoRepository.save(alumno);
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
	public AlumnoEntity actualizarAlumno(String rut, AlumnoEntity alumnoActualizado){
		AlumnoEntity alumno = alumnoRepository.findByRut(rut);
		alumno.setRut(alumnoActualizado.getRut());
		alumno.setApellidos(alumnoActualizado.getApellidos());
		alumno.setNombres(alumnoActualizado.getNombres());
		alumno.setFechaNacimiento(alumnoActualizado.getFechaNacimiento());
		alumno.setTipoColegio(alumnoActualizado.getTipoColegio());
		alumno.setNombreColegio(alumnoActualizado.getNombreColegio());
		alumno.setFechaEgreso(alumnoActualizado.getFechaEgreso());
		return alumnoRepository.save(alumno);
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

}
