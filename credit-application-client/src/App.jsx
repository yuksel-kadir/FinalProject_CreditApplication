import "./App.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Homepage from "./pages/creditApplicationPage";
import NavigationBar from "./components/NavigationBar";
import 'bootstrap/dist/css/bootstrap.min.css';
import "react-datepicker/dist/react-datepicker.css";
import CheckCreditApplicationPage from "./pages/checkCreditApplicationPage";
import UpdateInformationPage from "./pages/updateInformationPage";


function App() {
  return (
    <BrowserRouter>
      <NavigationBar/>
      <Routes>
        <Route path="/" element={<Navigate to="/creditApplication" />}/>
        <Route path="/creditApplication" element={<Homepage/>}/>
        <Route path="/updateInformations" element={<UpdateInformationPage/>}/>
        <Route path="/checkCreditApplication" element={<CheckCreditApplicationPage/>}/>   
      </Routes>
    </BrowserRouter>
  );
}

export default App;
