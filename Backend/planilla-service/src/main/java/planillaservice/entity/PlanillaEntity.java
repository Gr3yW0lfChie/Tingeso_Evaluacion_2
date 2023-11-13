package planillaservice.entity;

import jakarta.persistence.*;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_prueba", unique = true, nullable = false)
	private Long idPruebas;

	//Hace la relacion con la entidad alumno
	@Column(name = "rut_alumno")
	private String alumno;

	@Column(name = "fecha_examen")
	private LocalDate fechaExamen;

	@Column(name = "puntaje")
	private Integer puntaje;
}
