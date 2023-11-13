package cuotaservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {
	private String rut;
	private String apellidos;
	private String nombres;
	private LocalDate fechaNacimiento;
	private String tipoColegio;
	private String nombreColegio;
	private LocalDate fechaEgreso;
}
