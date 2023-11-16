import React, { useState } from "react";
import { useParams } from "react-router-dom";
import NavbarComponent3 from "./NavbarComponent3";
import styled from "styled-components";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import CrearCuotaService from "../services/CrearCuotaService";
import swal from 'sweetalert';

const CrearCuotasComponent = () => {
  const { rut, tipoColegio } = useParams();
  const [cuotas, setCuotas] = useState("");
  const [error, setError] = useState("");

  const handleCuotasChange = (event) => {
    const cuotasValue = event.target.value;
    setCuotas(cuotasValue);

    switch (tipoColegio) {
      case "Municipal":
        setError(cuotasValue > 10 ? "La cantidad máxima es 10 cuotas" : "");
        break;
      case "Subvencionado":
        setError(cuotasValue > 7 ? "La cantidad máxima es 7 cuotas" : "");
        break;
      case "Privado":
        setError(cuotasValue > 4 ? "La cantidad máxima es 4 cuotas" : "");
        break;
      default:
        setError("");
    }
  };

  const handleSubmit = async () => {
    // Muestra un cuadro de diálogo de confirmación
    swal({
      title: "¿Está seguro de que desea ingresar las cuotas?",
      icon: "warning",
      buttons: ["Cancelar", "Ingresar"],
      dangerMode: true,
    }).then(async (respuesta) => {
      if (respuesta) {
        console.log("Ingresando cuotas...");
        
        try {
          // Utiliza el servicio para enviar la solicitud al servidor
          await CrearCuotaService.IngresarCuota(rut, cuotas);
          // Maneja el resultado de la solicitud según sea necesario (redirección, mensaje al usuario, etc.)
          swal("Cuotas ingresadas correctamente!", { icon: "success", timer: "3000" });
        } catch (error) {
          console.error("Error al ingresar cuotas:", error);
          // Maneja el error según sea necesario (mostrar mensaje de error, redirigir a una página de error, etc.)
          swal({ text: "Error al ingresar cuotas", icon: "error" });
        }
      } else {
        // El usuario ha cancelado la operación
        swal({ text: "Operación cancelada.", icon: "info" });
      }
    });
  };

  return (
    <div className="home">
      <NavbarComponent3 />
      <Styles>
        <h1 className="text-center">
          <b>Crear Cuotas</b>
        </h1>
        <div className="form-container">
          <Form onSubmit={(e) => e.preventDefault()}>
            <Form.Group controlId="rut">
              <Form.Label>Rut del estudiante</Form.Label>
              <Form.Control type="text" value={rut} readOnly />
            </Form.Group>

            <Form.Group controlId="tipoColegio">
              <Form.Label>Tipo de colegio</Form.Label>
              <Form.Control type="text" value={tipoColegio} readOnly />
            </Form.Group>

            <Form.Group controlId="cuotas">
              <Form.Label>Cantidad de cuotas</Form.Label>
              <Form.Control
                type="number"
                placeholder={`Máx. ${tipoColegio === "Municipal" ? 10 : tipoColegio === "Subvencionado" ? 7 : 4}`}
                value={cuotas}
                onChange={handleCuotasChange}
              />
              {error && <span className="error-message">{error}</span>}
            </Form.Group>

            <Button className="boton" onClick={handleSubmit}>Crear</Button>
          </Form>
        </div>
      </Styles>
    </div>
  );
};

export default CrearCuotasComponent;

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

  .home {
    background-color: #006992;
    margin: 0;
    padding: 0;
  }

  .form-container {
    width: 50%;
    margin: auto;
  }

  * {
    font-family: sans-serif;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }

  .error-message {
    color: red;
    font-size: 14px;
    margin-top: 5px;
    display: block;
  }
`;
