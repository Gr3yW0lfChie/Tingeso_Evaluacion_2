import React, { useState  } from "react";
import NavbarComponent3 from "./NavbarComponent3";
import styled from "styled-components";
import AutorizacionService from "../services/SubirEstudianteService";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import swal from 'sweetalert';

export default function SubirEstudianteComponent(props){

    const initialState = {
        rut: "",
        apellidos: "",
        nombres: "",
        fechaNacimiento: new Date(),
        tipoColegio: "Municipal",
        nombreColegio: "",
        fechaEgreso: new Date(),
    };

    const [input, setInput] = useState(initialState);
    
    const changeRutHandler = event => {
        setInput({ ...input, rut: event.target.value });
        console.log(input.rut);
    };
    const changeApellidosHandler = event => {
        setInput({ ...input, apellidos: event.target.value });
        console.log(input.apellidos);
    };
    const changeNombresHandler = event => {
        setInput({ ...input, nombres: event.target.value });
        console.log(input.nombres);
    };
    const changeFechaNacimientoHandler = event => {
        setInput({ ...input, fechaNacimiento: new Date(event.target.value) });
        console.log(input.fechaNacimiento);
    };
    const changeTipoColegioHandler = event => {
        setInput({ ...input, tipoColegio: event.target.value });
        console.log(input.tipoColegio);
    };
    const changeNombreColegioHandler = event => {
        setInput({ ...input, nombreColegio: event.target.value });
        console.log(input.nombreColegio);
    };
    const changeFechaEgresoHandler = event => {
        setInput({ ...input, fechaEgreso: new Date(event.target.value) });
        console.log(input.fechaEgreso);
    };
    
    const ingresarEstudiante = async (e) => {
        e.preventDefault();
        swal({
            title: "¿Está seguro de que desea ingresar el estudiante?",
            icon: "warning",
            buttons: ["Cancelar", "Ingresar"],
            dangerMode: true,
        }).then(async (respuesta) => {
            if (respuesta) {
            swal("Estudiante ingresado correctamente!", { icon: "success", timer: "3000" });
            const estudiante = {
                rut: input.rut,
                apellidos: input.apellidos,
                nombres: input.nombres,
                fechaNacimiento: input.fechaNacimiento,
                tipoColegio: input.tipoColegio,
                nombreColegio: input.nombreColegio,
                fechaEgreso: input.fechaEgreso,
            };
            
            console.log("estudiante => " + JSON.stringify(estudiante));
            
            try {
                const response = await AutorizacionService.IngresarEstudiante(estudiante);
            } catch (error) {
                console.error("Error al ingresar estudiante:", error);
            }
            } else {
            swal({ text: "Estudiante no ingresado.", icon: "error" });
            }
        });
    };

    return(
            
            <Styles>
            <div className="home">
                <NavbarComponent3 />
                    <div className="mainclass">
                        <div className="form1">
                            <h1 className="text-center"><b>Estudiantes</b></h1>
                            <div className="formcontainer">
                                <hr></hr>
                                <div className="container">
                                    <Form>
                                        <Form.Group className="mb-3" controlId="rut" value = {input.rut} onChange={changeRutHandler}>
                                            <Form.Label>Rut del estudiante</Form.Label>
                                            <Form.Control type="rut" placeholder="Rut del alumno en formato xx.xxx.xxx-x" />
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="apellidos" value = {input.apellidos} onChange={changeApellidosHandler}>
                                            <Form.Label>Apellidos</Form.Label>
                                            <Form.Control type="apellidos" placeholder="Apellidos del estudiante" />
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="nombres" value = {input.nombres} onChange={changeNombresHandler}>
                                            <Form.Label>Nombres</Form.Label>
                                            <Form.Control type="nombres" placeholder="Nombres del estudiante" />
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="fechaNacimiento">
                                            <Form.Label>Fecha de nacimiento</Form.Label>
                                            <Form.Control type="date" placeholder="Fecha de nacimiento del estudiante" onChange={changeFechaNacimientoHandler} />
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="tipoColegio">
                                            <Form.Label>Tipo de colegio</Form.Label>
                                            <Form.Control as="select" onChange={changeTipoColegioHandler} value={input.tipoColegio}>
                                                <option value="Municipal">Municipal</option>
                                                <option value="Subvencionado">Subvencionado</option>
                                                <option value="Privado">Privado</option>
                                            </Form.Control>
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="nombreColegio" value = {input.nombreColegio} onChange={changeNombreColegioHandler}>
                                            <Form.Label>Nombre del colegio</Form.Label>
                                            <Form.Control type="nombreColegio" placeholder="Nombre del colegio" />
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="fechaEgreso">
                                            <Form.Label>Fecha egreso</Form.Label>
                                            <Form.Control type="date" placeholder="Fecha egreso del estudiante" onChange={changeFechaEgresoHandler} />
                                        </Form.Group>

                                    </Form>
                                </div>
                                <Button className="boton" onClick={ingresarEstudiante}>Registrar Estudiante</Button>
                            </div>
                        </div>
                    </div>
                
            </div>
            </Styles>
        )
    }


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

.home{
    background-color: #006992;
    margin: 0;
    padding: 0;
}

.mainclass{
    margin-top: 20px;
    display: flex;
    justify-content: center;
    font-family: Roboto, Arial, sans-serif;
    font-size: 15px;
}

.form1{
    border: 9px solid #CED0CE;
    background-color: #DADDD8;
    width: 50%;
    padding: 36px;
}

input[type=rut], input[type=fecha] {
    width: 100%;
    padding: 16px 8px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
}

Button {
    background-color: #42bfbb;
    color: white;
    padding: 14px 0;
    margin: 10px 0;
    border: none;
    cursor: grabbing;
    width: 100%;
}

Button:hover {
    opacity: 0.8;
}

.formcontainer {
    text-align: left;
    margin: 24px 100px 9px;
}

.container {
    padding: 24px 0;
    text-align:left;
}

span.psw {
    float: right;
    padding-top: 0;
    padding-right: 15px;
}
`