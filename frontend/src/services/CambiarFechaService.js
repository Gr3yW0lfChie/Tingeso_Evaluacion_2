import axios from "axios";

const CambiarFechaService = {
    cambiarFecha: async (nuevaFecha) => {
      try {
        const response = await axios.post('http://localhost:8080/cuota/interesCuotas', { fechaCambiada: nuevaFecha });
        return response.data;
      } catch (error) {
        console.error('Error en CambiarFechaService:', error);
        return { error: 'Error al cambiar la fecha. Por favor, inténtelo nuevamente.' };
      }
    }
  };
  
  export default CambiarFechaService;
  