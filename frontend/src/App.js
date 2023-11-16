import './App.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent';
import FileUploadComponent from './components/FileUploadComponent';
import FileInformationComponent from './components/PlanillaComponent2';
import EstudianteComponent from './components/EstudianteComponent';
import SubirEstudianteComponent from './components/SubirEstudianteComponent';
import VerPlanillaComponent from './components/VerPlanillaComponent';
import CrearCuotasComponent from './components/CrearCuotasComponent'; 
import VerCuotasComponent from './components/VerCuotasComponent';
import VerCuotasEstudianteComponent from './components/VerCuotaEstudianteComponent';
import CambiarFechaComponent from './components/CambiarFechaComponent';

function App() {
  return (
    <div>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomeComponent />} />
        <Route path= "/subir-archivo" element={<FileUploadComponent />} />
        <Route path= "/informacion-archivo" element={<FileInformationComponent />} />
        <Route path= "/lista-estudiantes" element={<EstudianteComponent />} />
        <Route path= "/estudiante" element={<SubirEstudianteComponent />} />
        <Route path= "/planilla-estudiantes" element={<VerPlanillaComponent />} />
        <Route path="/crear-cuotas/:rut/:tipoColegio" element={<CrearCuotasComponent />}/>
        <Route path= "/lista-cuotas" element={<VerCuotasComponent />} />
        <Route path= "/listar-cuota-estudiante" element={<VerCuotasEstudianteComponent />} />
        <Route path= "/cambiar-fecha" element={<CambiarFechaComponent />} />
      </Routes>
    </BrowserRouter>
  </div>
  );
}

export default App;
