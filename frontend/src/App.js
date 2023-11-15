import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent';
import FileUploadComponent from './components/FileUploadComponent';
import FileInformationComponent from './components/FileInformationComponent';
import SueldosComponent from './components/SueldosComponent';

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
        <Route path= "/planilla-estudiantes" element={<SueldosComponent />} />

      </Routes>
    </BrowserRouter>
  </div>
  );
}

export default App;
