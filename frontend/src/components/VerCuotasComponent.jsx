import React, { useState, useEffect } from "react";
import NavbarComponent4 from "./NavbarComponent4";
import styled from "styled-components";
import PagarCuotaService from "../services/PagarCuotaService";
import swal from 'sweetalert';

const VerCuotasComponent = () => {
    const [cuotas, setCuotas] = useState([]);
  
    useEffect(() => {
      fetch("http://localhost:8080/cuota")
        .then((response) => response.json())
        .then((data) => setCuotas(data));
    }, []);
  
    const handlePagarClick = (idCuota) => {
      
        swal({
          title: "¿Está seguro de que desea pagar esta cuota?",
          icon: "warning",
          buttons: ["Cancelar", "Sí"],
          dangerMode: true,
        }).then(async (confirmarPago) => {
          if (confirmarPago) {
            try {
              await PagarCuotaService.IngresarCuota(idCuota);
              swal("Cuota pagada exitosamente.", { icon: "success", timer: 3000 });
            } catch (error) {
              console.error("Error al pagar cuota:", error);
              swal("Error al pagar cuota. Por favor, inténtelo nuevamente.", { icon: "error" });
            }
          } else {
            swal({ text: "Pago de cuota cancelado.", icon: "info" });
          }
        });
      };
      
  
    return (
      <div className="home">
        <NavbarComponent4 />
        <Styles>
          <h1 className="text-center">
            <b>Listado de Cuotas</b>
          </h1>
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
                  <th>Pagar cuota</th>
                </tr>
              </thead>
              <tbody>
                {cuotas.map((cuota) => (
                  <tr key={cuota.idCuota}>
                    <td>{cuota.idCuota}</td>
                    <td>{cuota.rutAlumno}</td>
                    <td>{cuota.fechaVencimiento}</td>
                    <td>{cuota.cuotaPagada ? "Sí" : "No"}</td>
                    <td>{cuota.precioBase}</td>
                    <td>{cuota.porcentajeInteres}</td>
                    <td>{cuota.porcentajeDescuento}</td>
                    <td>{cuota.precioAPagar}</td>
                    <td>
                    <button onClick={() => handlePagarClick(cuota.idCuota)}>Pagar</button>
                    </td>
                  </tr>
                ))}
              </tbody>
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

export default VerCuotasComponent;
