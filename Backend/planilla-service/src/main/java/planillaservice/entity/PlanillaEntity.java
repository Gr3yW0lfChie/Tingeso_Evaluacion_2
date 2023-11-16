package planillaservice.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "planilla")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaEntity {

	@Id
	@Column(name = "rut_alumno")
	private String rutAlumno;

	@Column(name = "nombre_alumno")
	private String nombreAlumno;

	@Column(name = "examenes_rendidos")
	private Integer examenesRendidos;

	@Column(name = "promedio_examenes")
	private Integer promedioExamenes;

	@Column(name = "total_arancel")
	private Integer totalArancel;

	@Column(name = "tipo_pago")
	private String tipoPago;

	@Column(name = "cuotas_pactadas")
	private Integer cuotasPactadas;

	@Column(name = "cuotas_pagadas")
	private Integer cuotasPagadas;

	@Column(name = "monto_pagado")
	private Integer montoPagado;

	@Column(name = "fecha_ultimo_pago")
	private LocalDate fechaUltimoPago;

	@Column(name = "saldo_por_pagar")
	private Integer saldoPorPagar;

	@Column(name = "cuotas_impagas")
	private Integer cuotasImpagas;
}
