package estudianteservice.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {

	@Id
	@Column(name = "rut", unique = true, nullable = false)
	private String rut;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "nombres")
	private String nombres;

	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;

	@Column(name = "tipo_colegio")
	private String tipoColegio;

	@Column(name = "nombre_colegio")
	private String nombreColegio;

	@Column(name = "fecha_egreso_colegio")
	private LocalDate fechaEgreso;

}
