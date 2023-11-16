package cuotaservice.service;

import cuotaservice.entity.CuotaEntity;
import cuotaservice.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CuotaService {

	@Autowired
	private CuotaRepository cuotaRepository;

	//----------------------------------------------------------------------------------------------------------
	//Busqueda
	public ArrayList<CuotaEntity> obtenerCuotas() {
		return (ArrayList<CuotaEntity>) cuotaRepository.findAll();
	}

	public ArrayList<CuotaEntity> findByRutAlumno(String rut) {
		return cuotaRepository.findByRutAlumno(rut);
	}

	public Optional<CuotaEntity> obtenerCuotaPorId(Long id){
		return cuotaRepository.findById(id);
	}

	//----------------------------------------------------------------------------------------------------------
	//Crear
	public CuotaEntity crearCuota(CuotaEntity cuotaEntity){
		return cuotaRepository.save(cuotaEntity);
	}

	//----------------------------------------------------------------------------------------------------------
	//Eliminar
	/*
	public void eliminarCuota(Long id){
		cuotaRepository.deleteById(id);
	}
	*/
	//----------------------------------------------------------------------------------------------------------
	//Modificar

	public void crearCuotas(Integer cantidadCuotas, String rutAlumno, Integer precioBase){
		LocalDate fechaActual = LocalDate.of(2024, 1, 10);
		if(cantidadCuotas == 1){
			CuotaEntity cuota = new CuotaEntity();
			cuota.setRutAlumno(rutAlumno);
			cuota.setFechaVencimiento(fechaActual);
			cuota.setCuotaPagada(true);
			cuota.setPrecioBase(precioBase);
			cuota.setPorcentajeInteres(0);
			cuota.setPorcentajeDescuento(0);
			cuota.setPrecioAPagar(precioBase); //Se hace el descuento del 50%

			crearCuota(cuota);
		}else{
			for(int i = 0; i < cantidadCuotas; i++){
				CuotaEntity cuota = new CuotaEntity();
				cuota.setRutAlumno(rutAlumno);
				cuota.setFechaVencimiento(fechaActual);
				cuota.setCuotaPagada(false);
				cuota.setPrecioBase(precioBase/cantidadCuotas);
				cuota.setPorcentajeInteres(0);
				cuota.setPorcentajeDescuento(0);
				cuota.setPrecioAPagar(precioBase/cantidadCuotas);

				crearCuota(cuota);
				fechaActual = fechaActual.plusMonths(1);
			}
		}
	}


	public void pagarCuota(Long id){
		Optional<CuotaEntity> cuota = cuotaRepository.findById(id);
		if (cuota.isPresent()){
			CuotaEntity cuotaPagada = cuota.get();
			cuotaPagada.setCuotaPagada(true);
			cuotaRepository.save(cuotaPagada);
		}
	}
	public void modificarCuotasVencidas(LocalDate fechaNueva){
		ArrayList<CuotaEntity> cuotas = obtenerCuotas();
		for (CuotaEntity cuota : cuotas){
			if (cuota.getFechaVencimiento().isBefore(fechaNueva) && !cuota.getCuotaPagada()){
				Period period = Period.between(cuota.getFechaVencimiento(), fechaNueva);
				if (period.getMonths() == 0) {
					cuota.setPorcentajeInteres(0);
				} else if (period.getMonths() == 1) {
					cuota.setPorcentajeInteres(3);
				} else if (period.getMonths() == 2) {
					cuota.setPorcentajeInteres(6);
				} else if (period.getMonths() == 3) {
					cuota.setPorcentajeInteres(9);
				}else {
					cuota.setPorcentajeInteres(15);
				}
				cuota.setPrecioAPagar(cuota.getPrecioBase() + (cuota.getPrecioBase() * cuota.getPorcentajeInteres() / 100) - (cuota.getPrecioBase() * cuota.getPorcentajeDescuento() / 100));
				cuotaRepository.save(cuota);
			}
		}
	}


	public void actualizarDescuentoCuotas(String rut, LocalDate fechaExamen, Integer puntaje){
		ArrayList<CuotaEntity> cuotas = findByRutAlumno(rut);
		for (CuotaEntity cuota : cuotas){
			if (cuota.getFechaVencimiento().isAfter(fechaExamen) && !cuota.getCuotaPagada()){
					if (puntaje >= 950){
						cuota.setPorcentajeDescuento(10);
					} else if (puntaje >= 900){
						cuota.setPorcentajeDescuento(5);
					} else if (puntaje >= 850){
						cuota.setPorcentajeDescuento(2);
					} else {
						cuota.setPorcentajeDescuento(0);
					}
				cuota.setPrecioAPagar(cuota.getPrecioBase() + (cuota.getPrecioBase() * cuota.getPorcentajeInteres() / 100) - (cuota.getPrecioBase() * cuota.getPorcentajeDescuento() / 100));
				cuotaRepository.save(cuota);
			}
		}
	}


}
