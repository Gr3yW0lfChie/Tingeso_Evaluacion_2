import React, { useState } from "react";
import NavbarComponent4 from "./NavbarComponent4";
import styled from "styled-components";
import PagarCuotaService from "../services/PagarCuotaService";
import swal from 'sweetalert';

const VerCuotasEstudianteComponent = () => {
  const [rutEstudiante, setRutEstudiante] = useState("");
  const [cuotasEstudiante, setCuotasEstudiante] = useState([]);

  const handleBuscarCuotasClick = async () => {
    try {
        const response = await fetch(`http://localhost:8080/cuota/porRut/${rutEstudiante}`);
        const data = await response.json();
      setCuotasEstudiante(data);
    } catch (error) {
      console.error("Error al obtener cuotas del estudiante:", error);
      swal("Error al obtener cuotas del estudiante. Por favor, inténtelo nuevamente.", { icon: "error" });
    }
  };

  const handlePagarClick = (idCuota) => {
    // Implementa la lógica de pago similar a tu componente VerCuotasComponent
  };

  return (
    <div className="home">
      <NavbarComponent4 />
      <Styles>
        <h1 className="text-center">
          <b>Ver Cuotas por Estudiante</b>
        </h1>
        <div className="f">
          <label>Rut del Estudiante:</label>
          <input
            type="text"
            value={rutEstudiante}
            onChange={(e) => setRutEstudiante(e.target.value)}
          />
          <button onClick={handleBuscarCuotasClick}>Buscar Cuotas</button>
        </div>
        <div className="f">
          <table border="1" className="content-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Rut del Alumno</th>
                <th>Fecha de Vencimiento</th>
                <th>Cuota Pagada</th>
                <th>Precio Base</th>
                <th>Porcentaje de Interés</th>
                <th>Porcentaje de Descuento</th>
                <th>Precio a Pagar</th>
              </tr>
            </thead>
            {cuotasEstudiante && cuotasEstudiante.length > 0 ? (
              <tbody>
                {Array.isArray(cuotasEstudiante) ? (
                  cuotasEstudiante.map((cuota) => (
                    <tr key={cuota.idCuota}>
                      <td>{cuota.idCuota}</td>
                      <td>{cuota.rutAlumno}</td>
                      <td>{cuota.fechaVencimiento}</td>
                      <td>{cuota.cuotaPagada ? "Sí" : "No"}</td>
                      <td>{cuota.precioBase}</td>
                      <td>{cuota.porcentajeInteres}</td>
                      <td>{cuota.porcentajeDescuento}</td>
                      <td>{cuota.precioAPagar}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="9">No se encontraron cuotas para el estudiante.</td>
                  </tr>
                )}
              </tbody>
            ) : (
              <tbody>
                <tr>
                  <td colSpan="9">No se encontraron cuotas para el estudiante.</td>
                </tr>
              </tbody>
            )}
          </table>
        </div>
      </Styles>
    </div>
  );
};

const Styles = styled.div`
  .text-center {
    text-align: center;
    justify-content: center;
    padding-top: 8px;
    font-family: "Arial Black", Gadget, sans-serif;
    font-size: 30px;
    letter-spacing: 0px;
    word-spacing: 2px;
    color: #000000;
    font-weight: 700;
    text-decoration: none solid rgb(68, 68, 68);
    font-style: normal;
    font-variant: normal;
    text-transform: uppercase;
  }

  .f {
    justify-content: center;
    align-items: center;
    display: flex;
  }

  * {
    font-family: sans-serif;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }

  .content-table {
    border-collapse: collapse;
    margin: 25px 0;
    font-size: 0.9em;
    min-width: 400px;
    border-radius: 5px 5px 0 0;
    overflow: hidden;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
  }

  .content-table thead tr {
    background-color: #009879;
    color: #ffffff;
    text-align: left;
    font-weight: bold;
  }

  .content-table th,
  .content-table td {
    padding: 12px 15px;
  }

  .content-table tbody tr {
    border-bottom: 1px solid #dddddd;
  }

  .content-table tbody tr:nth-of-type(even) {
    background-color: #f3f3f3;
  }

  .content-table tbody tr:last-of-type {
    border-bottom: 2px solid #009879;
  }

  .content-table tbody tr.active-row {
    font-weight: bold;
    color: #009879;
  }
`;

export default VerCuotasEstudianteComponent;
