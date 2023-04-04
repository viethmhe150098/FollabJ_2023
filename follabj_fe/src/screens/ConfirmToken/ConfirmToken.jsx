import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router'

const ConfirmToken = () => {
  const[message, setMeassage] = useState('')
  const { token } = useParams();
  useEffect(()=> {
    const fetch = async () => {
      try{
        const res = await axios.get(`http://localhost:8080/confirm?token=${token}`);
        // const data = res.data.message;
        
          setMeassage(res.data.message)
        
      }catch(err){
        setMeassage(err.response.data.message);
      }
    }
    fetch()
  }, [])   
  
  console.log(token);
  return (
    <div>{message}</div>
  )
}

export default ConfirmToken