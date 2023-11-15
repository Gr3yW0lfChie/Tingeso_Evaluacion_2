package estudianteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaModel {

	private String rutAlumno;
	private String nombreAlumno;
	private String apellidoAlumno;
	private Integer examenesRendidos;
	private Integer promedioExamenes;
	private Integer totalArancel;
	private String tipoPago;
	private Integer cuotasPactadas;
	private Integer cuotasPagadas;
	private Integer montoPagado;
	private LocalDate fechaUltimoPago;
	private Integer saldoPorPagar;
	private Integer cuotasImpagas;
}
