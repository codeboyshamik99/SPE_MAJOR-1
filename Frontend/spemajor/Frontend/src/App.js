import './App.css';
import ListAllemployees from './Components/ListAllemployees';
import AddEmployee from './Components/AddEmployee';
import ListAllDepartments from './Components/ListAllDepartments';
import {BrowserRouter,Routes,Route} from 'react-router-dom';
import UpdateEmployee from './Components/UpdateEmployee';
import AddDepartment from './Components/AddDepartment';
import UpdateDepartment from './Components/UpdateDepartment';
function App() {
  return (
    <div className="App">
    <BrowserRouter>
      <Routes>
        <Route path='/' element = {<ListAllemployees/>}></Route>
        <Route path='/employees' element = {<ListAllemployees/>}></Route>
        <Route path='/add-employee' element = {<AddEmployee/>}></Route>
        <Route path='/update-employee/:id' element = {<UpdateEmployee/>}></Route>
        <Route path='/departments' element={<ListAllDepartments/>}></Route>
        <Route path='/add-department' element = {<AddDepartment/>}></Route>
        <Route path='/update-department/:id' element = {<UpdateDepartment/>}></Route>
      </Routes>
    </BrowserRouter>
    </div>
  );
}

export default App;
