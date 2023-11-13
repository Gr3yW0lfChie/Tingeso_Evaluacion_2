package planillaservice.service;

import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SubirPrueba {
	@Autowired
	private PlanillaService planillaService;
	private final Logger logg = LoggerFactory.getLogger(SubirPrueba.class);

	@Generated
	public boolean guardar(MultipartFile file){
		String filename = file.getOriginalFilename();
		if(filename != null){
			if(!file.isEmpty()){
				try{
					byte [] bytes = file.getBytes();
					Path path  = Paths.get(file.getOriginalFilename());
					Files.write(path, bytes);
					logg.info("Archivo guardado");
				}
				catch (IOException e){
					logg.error("ERROR", e);
				}
			}
			return true;
		}
		else{
			return false;
		}
	}

	@Generated
	public boolean leerCsvExamen(String direccion){
		String texto = "";
		BufferedReader bf = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD-MM-YYYY");


		try{
			bf = new BufferedReader(new FileReader(direccion));
			String temp = "";
			String bfRead;
			int count = 1;
			while((bfRead = bf.readLine()) != null){
				if (count == 1){
					count = 0;
				}
				else{
					planillaService.guardarNotas( bfRead.split(";")[0], LocalDate.parse(bfRead.split(";")[1], formatter), Integer.parseInt(bfRead.split(";")[2]));
					temp = temp + "\n" + bfRead;
				}
			}
			texto = temp;
			System.out.println("Archivo leido exitosamente");
			return true;
		}catch(Exception e){
			System.err.println("No se encontro el archivo");
		}finally{
			if(bf != null){
				try{
					bf.close();
				}catch(IOException e){
					logg.error("ERROR", e);
				}
			}
		}
		return false;
	}

}
