import axios from "axios";

class CrearCuotaService {
    
    IngresarCuota(rut, cantidadCuotas) {
        const estudiante = {
            rut: rut,
            cantidadCuotas: cantidadCuotas
        };

        return axios.post(`http://localhost:8080/estudiante/crearCuotas/${rut}`, { cantidadCuotas });
    }
}

export default new CrearCuotaService();
