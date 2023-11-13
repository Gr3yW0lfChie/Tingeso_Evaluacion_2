package estudianteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaEntity {
	private Long idPrueba;
	private String alumno;
	private LocalDate fechaExamen;
	private Integer puntaje;
}
