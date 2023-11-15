package estudianteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaModel {
	private Long idCuota;
	private String rutAlumno;
	private LocalDate fechaVencimiento;
	private Boolean cuotaPagada;
	private Integer precioBase;
	private Integer porcentajeInteres;
	private Integer porcentajeDescuento;
	private Integer precioAPagar;
}
