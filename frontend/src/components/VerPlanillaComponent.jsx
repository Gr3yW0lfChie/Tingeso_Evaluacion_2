import React, { useState, useEffect } from "react";
import NavbarComponent from "./NavbarComponent";
import styled from "styled-components";

const VerPlanillaComponent = () => {
  const [planillas, setPlanillas] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/planilla")
      .then((response) => response.json())
      .then((data) => {
        if (Array.isArray(data)) {
          setPlanillas(data);
        } else {
          console.error("La respuesta no es un array:", data);
        }
      })
      .catch((error) => console.error("Error al obtener las planillas:", error));
  }, []);

  return (
    <div className="home">
      <NavbarComponent />
      <Styles>
        <h1 className="text-center">
          <b>Planilla de los estudiantes</b>
        </h1>
        <div className="f">
          <table border="1" className="content-table">
            <thead>
              <tr>
                <th width="10%">Rut</th>
                <th>Nombre</th>
                <th>Examenes rendidos</th>
                <th>Promedio puntajes</th>
                <th>Monto total arancel a pagar</th>
                <th>Tipo de Pago</th>
                <th>Nro. total cuotas pactas</th>
                <th>Nro. cuotas pagadas</th>
                <th>Monto total pagado</th>
                <th>Fecha último pago</th>
                <th>Saldo por pagar</th>
                <th>Nro. cuotas con retraso</th>
              </tr>
            </thead>
            <tbody>
              {planillas.map((planilla) => (
                <tr key={planilla.rutAlumno}>
                  <td>{planilla.rutAlumno}</td>
                  <td>{planilla.nombreAlumno}</td>
                  <td>{planilla.examenesRendidos}</td>
                  <td>{planilla.promedioExamenes}</td>
                  <td>{planilla.totalArancel}</td>
                  <td>{planilla.tipoPago}</td>
                  <td>{planilla.cuotasPactadas}</td>
                  <td>{planilla.cuotasPagadas}</td>
                  <td>{planilla.montoPagado}</td>
                  <td>{planilla.fechaUltimoPago}</td>
                  <td>{planilla.saldoPorPagar}</td>
                  <td>{planilla.cuotasImpagas}</td>
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
    font-size: 0.8em;
    min-width: 200px;
    border-radius: 5px 5px 0 0;
    overflow: hidden;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    margin-left: 4%;
    margin-right: 4%;
  }

  .content-table thead tr {
    background-color: #009879;
    color: #ffffff;
    text-align: center;
    font-weight: bold;
  }

  .content-table th,
  .content-table td {
    padding: 12px 15px;
    text-align: center;
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

export default VerPlanillaComponent;
