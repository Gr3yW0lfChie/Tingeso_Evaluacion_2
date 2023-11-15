import axios from "axios";

class SubirEstudianteService {
    
    IngresarEstudiante(estudiante){
        return axios.post(`http://localhost:8080/estudiante`, estudiante);
    }
}

export default new SubirEstudianteService();