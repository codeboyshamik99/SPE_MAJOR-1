import React, { useEffect, useState } from 'react';
import './AddEmployee.css'; 
import { createEmployee} from '../Services/EmployeeService';
import { getAllDepartments } from '../Services/DepartmentService';
import { useNavigate } from 'react-router-dom';

function AddEmployee() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    departmentId:''
  });
  const [isVisible, setIsVisible] = useState(true);
  const [error, setError] = useState('');
  const [departments, setDepartments] = useState([]);
  const navigate = useNavigate();
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  useEffect(() => {
    // Fetch department list
    getAllDepartments()
      .then((response) => {
        setDepartments(response.data);
      })
      .catch((error) => {
        console.error('Error fetching department list:', error);
      });
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.firstName || !formData.lastName || !formData.email||!formData.departmentId) {
      setError('Please fill in all fields.');
      return;
    }
    setError('');
    createEmployee(formData)
    .then((response) => {
        navigate('/employees');
      console.log("Response:", response);
    })
    .catch((error) => {
      console.error("Error:", error);
    });
    console.log("Form submitted:", formData);
  };

  const toggleVisibility = () => {
    navigate('/employees');
  };

  const stopPropagation = (e) => {
    e.stopPropagation();
  };

  return (
    <>
      {isVisible && (
        <div className="form-container-employee" onClick={stopPropagation}>
          <button className="close-button-employee" onClick={toggleVisibility}>X</button>
          <div className="container-employee">
            <h2 className="form-header">Employee Registration</h2>
            <form className="employee-form" onSubmit={handleSubmit}>
              <label>
                First Name:
                <input
                  type="text"
                  name="firstName"
                  placeholder="Enter first name"
                  value={formData.firstName}
                  onChange={handleChange}
                />
              </label>
              <br />
              <label>
                Last Name:
                <input
                  type="text"
                  name="lastName"
                  placeholder="Enter last name"
                  value={formData.lastName}
                  onChange={handleChange}
                />
              </label>
              <br />
              <label>
                Email:
                <input
                  type="email"
                  name="email"
                  placeholder="Enter email"
                  value={formData.email}
                  onChange={handleChange}
                />
              </label>
              <br />
              <label>
                Department:
                <select 
                  name="departmentId"
                  value={formData.departmentId}
                  onChange={handleChange}
                >
                  <option value="">Select Department</option>
                  {departments.map(department => (
                    <option key={department.id} value={department.id}>
                      {department.departmentName}
                    </option>
                  ))}
                </select>
              </label>
              <br/>
              {error && <p className="error-message">{error}</p>}
              <button type="submit">Submit</button>
            </form>
          </div>
        </div>
      )}
    </>
  );
}

export default AddEmployee;
