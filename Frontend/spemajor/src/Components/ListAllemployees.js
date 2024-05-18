import React, { useState, useEffect } from 'react';
import './ListAllemployees.css';
import {Link} from 'react-router-dom';
import { listEmployees,deleteEmployee} from '../Services/EmployeeService'; 
import { useNavigate } from 'react-router-dom';

const EmployeeTable = () => {
  const [employees, setEmployees] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getAllEmployees();
  }, []);

  const getAllEmployees = ()=>{
    listEmployees()
    .then(response => {
      console.warn(response.data);
      setEmployees(response.data); 
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });
  }

  const addNewEmployee = () =>{
    navigate('/add-employee');
  }

  const Update = (employeeId) =>{
    navigate(`/update-employee/${employeeId}`);
  }

  const Delete = (employeeId)=>{
      deleteEmployee(employeeId)
      .then((response) => {
        console.log("Response:", response);
        console.log("Employee Deleted Successfully!!!");
        getAllEmployees();
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }
  return (
    <div>
      <div className="Header">
        <h1 className="HeaderText">Employee Management System</h1>
        <span className="DepartmentLink">
          <Link to="/departments">Department</Link>
        </span>
        <span className="DepartmentLink1">
          <Link to="/employees">Employee</Link>
        </span>
        
      </div>
      <div className="EmployeeTableContainer">
        <h2 className="TableHeading">List of Employees</h2>
        <button type="button" class="btn" onClick={addNewEmployee}>Add Employee</button>
        <table className="EmployeeTable">
          <thead>
            <tr>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th>Employee ID</th>
              <th>Department_ID</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {employees.map(employee => (
              <tr key={employee.id}>
                <td>{employee.firstName}</td>
                <td>{employee.lastName}</td>
                <td>{employee.email}</td>
                <td>{employee.id}</td>
                <td>{employee.departmentId}</td>
                <td>
                  <button className='btn2' onClick={()=>Update(employee.id)}>Update</button>
                  <button className='btn1' onClick={()=>Delete(employee.id)}>Delete</button>
                </td>
               
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="Footer">
        <p className="FooterText">About Us</p>
      </div>
    </div>
  );
};

export default EmployeeTable;
