import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';

const ConfirmToken = () => {
  const [message, setMessage] = useState('');
  const { token } = useParams();

  useEffect(() => {
    const fetch = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/confirm?token=${token}`);
        setMessage(res.data.message);
      } catch (err) {
        setMessage(err.response.data.message);
      }
    };
    fetch();
  }, []);

  return (
    <div style={{ 
      display: 'flex', 
      justifyContent: 'center', 
      alignItems: 'center', 
      height: '100vh', 
      fontSize: '2rem', 
      fontWeight: 'bold', 
      color: '#333' 
    }}>
      {message}
    </div>
  );
};

export default ConfirmToken;
