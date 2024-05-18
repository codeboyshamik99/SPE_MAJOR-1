import React, { useState } from 'react';
import './AddDepartment.css';
import { createDepartment } from '../Services/DepartmentService';
import { useNavigate } from 'react-router-dom';

function AddDepartment() {
  const [formData, setFormData] = useState({
    departmentName: '',
    departmentDescription: ''
  });
  const [isVisible, setIsVisible] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.departmentName || !formData.departmentDescription) {
      setError('Please fill in all fields.');
      return;
    }
    setError('');
    createDepartment(formData)
      .then((response) => {
        navigate('/departments');
        console.log("Response:", response);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
    console.log("Form submitted:", formData);
  };

  const toggleVisibility = () => {
    navigate('/departments');
  };

  const stopPropagation = (e) => {
    e.stopPropagation();
  };

  return (
    <>
      {isVisible && (
        <div className="form-container-department" onClick={stopPropagation}>
          <button className="close-button-department" onClick={toggleVisibility}>X</button>
          <div className="container-department">
            <h2 className="form-header">Department Registration</h2>
            <form className="department-form" onSubmit={handleSubmit}>
              <label>
                Department Name:
                <input
                  type="text"
                  name="departmentName"
                  placeholder="Enter department name"
                  value={formData.departmentName}
                  onChange={handleChange}
                />
              </label>
              <br />
              <label>
                Department Description:
                <textarea
                  name="departmentDescription"
                  placeholder="Enter department description"
                  value={formData.departmentDescription}
                  onChange={handleChange}
                />
              </label>
              <br />
              {error && <p className="error-message">{error}</p>}
              <button type="submit">Submit</button>
            </form>
          </div>
        </div>
      )}
    </>
  );
}

export default AddDepartment;
