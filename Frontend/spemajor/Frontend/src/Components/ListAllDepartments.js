import React, { useState, useEffect } from 'react';
import './ListAllDepartments.css';
import { Link } from 'react-router-dom';
import { getAllDepartments, deleteDepartment } from '../Services/DepartmentService'; 
import { useNavigate } from 'react-router-dom';

const DepartmentTable = () => {
  const [departments, setDepartments] = useState([]); 
  const navigate = useNavigate();

  useEffect(() => {
    getDepartments(); 
  }, []);

  const getDepartments = () => { 
    getAllDepartments() 
      .then(response => {
        console.warn(response.data);
        setDepartments(response.data); 
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }

  const addNewDepartment = () => { 
    navigate('/add-department'); 
  }

  const updateDepartment = (departmentId) => { 
    navigate(`/update-department/${departmentId}`); 
  }

  const Delete = (departmentId) => { 
    deleteDepartment(departmentId) 
      .then((response) => {
        console.log("Response:", response);
        console.log("Department Deleted Successfully!!!");
        getDepartments();
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }

  return (
    <div>
      <div className="Header">
        <h1 className="HeaderText">Department Management System</h1>
        <span className="DepartmentLink">
          <Link to="/departments">Department</Link>
        </span>
        <span className="DepartmentLink1">
          <Link to="/employees">Employee</Link>
        </span>
      </div>
      <div className="DepartmentTableContainer">
        <h2 className="TableHeading">List of Departments</h2>
        <button type="button" className="btn" onClick={addNewDepartment}>Add Department</button>
        <table className="DepartmentTable">
          <thead>
            <tr>
              <th>Department ID</th>
              <th>Department Name</th>
              <th>Department Description</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {departments.map(department => (
              <tr key={department.id}>
                <td>{department.id}</td>
                <td>{department.departmentName}</td>
                <td>{department.departmentDescription}</td>
                <td>
                  <button className='btn2' onClick={() => updateDepartment(department.id)}>Update</button>
                  <button className='btn1' onClick={() => Delete(department.id)}>Delete</button>
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

export default DepartmentTable;
