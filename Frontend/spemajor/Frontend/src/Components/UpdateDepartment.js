import React, { useState, useEffect } from 'react';
import './UpdateDepartment.css'; // Update CSS file name
import { updateDepartment, getDepartmentById } from '../Services/DepartmentService'; // Update service functions
import { useParams, useNavigate } from 'react-router-dom';

function UpdateDepartment() { 
  const { id } = useParams();
  const [formData, setFormData] = useState({
    departmentName: '', 
    departmentDescription: '' 
  });
  const [isVisible, setIsVisible] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    getDepartmentById(id)
      .then((response) => {
        const { departmentName, departmentDescription } = response.data; 
        setFormData({ departmentName, departmentDescription });
        console.warn(formData);
      })
      .catch((error) => {
        console.error('Error fetching department data:', error);
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
    if (!formData.departmentName || !formData.departmentDescription) { 
      setError('Please fill in all fields.');
      return;
    }
    setError('');
    updateDepartment(id, formData) 
      .then((response) => {
        console.log("Response:", response);
        navigate('/departments'); 
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
        <div className="form-container-update-department" onClick={stopPropagation}>
          <button className="close-button-update-department" onClick={toggleVisibility}>X</button>
          <div className="container-update-department">
            <h2 className="form-header">Update Department</h2>
            <form className="update-department-form" onSubmit={handleSubmit}>
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
              <button type="submit">Update</button>
            </form>
          </div>
        </div>
      )}
    </>
  );
}

export default UpdateDepartment;
