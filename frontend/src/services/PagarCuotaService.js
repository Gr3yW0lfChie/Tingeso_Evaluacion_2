import axios from "axios";

class PagarCuotaService {
  IngresarCuota(id) {
    return axios.post(`http://localhost:8080/cuota/pagarCuota/${id}`);
  }
}

export default new PagarCuotaService();
