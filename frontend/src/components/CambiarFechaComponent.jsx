import React, { useState } from "react";
import NavbarComponent3 from "./NavbarComponent3";
import styled from "styled-components";
import CambiarFechaService from "../services/CambiarFechaService";
import swal from 'sweetalert';

const CambiarFechaComponent = () => {
  const [nuevaFecha, setNuevaFecha] = useState("");

  const handleCambiarFechaClick = async () => {
    try {
      // Lógica para llamar al servicio CambiarFechaService con la fecha seleccionada
      const response = await CambiarFechaService.cambiarFecha(nuevaFecha);
      // Puedes manejar la respuesta según tus necesidades
      swal("Fecha cambiada correctamente", { icon: "success", timer: "3000" });
    } catch (error) {
      console.error("Error al cambiar la fecha:", error);
      swal("Error al cambiar la fecha. Por favor, inténtelo nuevamente.", { icon: "error" });
    }
  };

  return (
    <div className="home">
      <NavbarComponent3 />
      <Styles>
        <h1 className="text-center">
          <b>Cambiar Fecha</b>
        </h1>
        <div className="f">
          <label>Nueva Fecha:</label>
          <input
            type="date"
            value={nuevaFecha}
            onChange={(e) => setNuevaFecha(e.target.value)}
          />
          <button onClick={handleCambiarFechaClick}>Cambiar Fecha</button>
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
`;

export default CambiarFechaComponent;
