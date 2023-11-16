package planillaservice.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import planillaservice.entity.PlanillaEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SubirPruebaService {
	@Autowired
	private PlanillaService planillaService;

	@Autowired
	private RestTemplate restTemplate;

	private final Logger logg = LoggerFactory.getLogger(SubirPruebaService.class);


	public String guardar(MultipartFile file) {
		String filename = file.getOriginalFilename();
		if (filename != null) {
			if ((!file.isEmpty()) && (filename.toUpperCase().equals("DATA.TXT"))) {
				try {
					byte[] bytes = file.getBytes();
					Path path = Paths.get(file.getOriginalFilename());
					Files.write(path, bytes);

					logg.info("Archivo guardado");
				} catch (IOException e) {
					logg.error("ERROR", e);
				}
			}
			return "Archivo guardado con exito!";
		} else {
			return "No se pudo guardar el archivo";
		}
	}

	public void leerCsv(String direccion) {
		String texto = "";
		BufferedReader bf = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		try {
			bf = new BufferedReader(new FileReader(direccion));
			String temp = "";
			String bfRead;
			while ((bfRead = bf.readLine()) != null) {
				String rut = bfRead.split(";")[0];
				LocalDate fecha = LocalDate.parse(bfRead.split(";")[1], formatter);
				Integer puntaje = Integer.parseInt(bfRead.split(";")[2]);
				PlanillaEntity planilla = planillaService.planillaPorRut(rut);
				Integer promedio = planillaService.calcularPromedioExamen(puntaje, rut);
				planilla.setExamenesRendidos(planilla.getExamenesRendidos() + 1);
				planilla.setPromedioExamenes(promedio);
				planillaService.save(planilla);

				String url = UriComponentsBuilder.fromUriString("http://cuota-service/cuota/actualizarDescuentoCuotas")
						.queryParam("rut", rut)
						.queryParam("fechaExamen", fecha)
						.queryParam("promedio", promedio)
						.build().toUriString();

				String exitoCuotas = restTemplate.postForObject(url, null, String.class);
				temp = temp + "\n" + bfRead;
			}
			texto = temp;
			System.out.println("Archivo leido exitosamente");
		} catch (Exception e) {
			System.err.println("No se encontro el archivo");
		} finally {
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					logg.error("ERROR", e);
				}
			}
		}
	}

}
