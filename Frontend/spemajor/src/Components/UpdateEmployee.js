import React, { useState, useEffect } from 'react';
import './UpdateEmployee.css'; 
import { updateEmployee, getEmployee } from '../Services/EmployeeService';
import { getAllDepartments } from '../Services/DepartmentService';
import { useParams, useNavigate } from 'react-router-dom';

function UpdateEmployee() {
  const { id } = useParams(); 
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    departmentId: ''
  });
  const [isVisible, setIsVisible] = useState(true);
  const [error, setError] = useState('');
  const [departments, setDepartments] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getEmployee(id)
      .then((response) => {
        const { firstName, lastName, email, departmentId } = response.data;
        setFormData({ firstName, lastName, email, departmentId });
      })
      .catch((error) => {
        console.error('Error fetching employee data:', error);
      });

    // Fetch department list
    getAllDepartments()
      .then((response) => {
        setDepartments(response.data);
      })
      .catch((error) => {
        console.error('Error fetching department list:', error);
      });
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.firstName || !formData.lastName || !formData.email || !formData.departmentId) {
      setError('Please fill in all fields.');
      return;
    }
    setError('');
    updateEmployee(id, formData)
      .then((response) => {
        console.log("Response:", response);
        navigate('/');
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
        <div className="form-container-update-employee" onClick={stopPropagation}>
          <button className="close-button-update-employee" onClick={toggleVisibility}>X</button>
          <div className="container-update-employee">
            <h2 className="form-header">Update Employee</h2>
            <form className="update-employee-form" onSubmit={handleSubmit}>
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
              <br />
              {error && <p className="error-message">{error}</p>}
              <button type="submit">Update</button>
            </form>
          </div>
        </div>
      )}
    </>
  );
}

export default UpdateEmployee;
