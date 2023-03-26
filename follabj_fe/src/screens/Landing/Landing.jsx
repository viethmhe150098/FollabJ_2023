import React from 'react';
import { useSelector } from "react-redux";
import { useEffect,useState } from 'react';
// Sections
import TopNavbar from "../../components/Nav/TopNavbar";
import Header from "../../components/Sections/Header";
import Services from "../../components/Sections/Services";
import Pricing from "../../components/Sections/Pricing";
import Contact from "../../components/Sections/Contact";
import Footer from "../../components/Sections/Footer"
import About from "../../components/Sections/About"
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default function Landing() {
  const loginError = useSelector(state => state.auth.login.error);
  const [count, setCount] = useState(0);

  useEffect(() => {
    // if (!loginError && count === 0) {
    //   toast.success('Welcome back! 🧡', {
    //     autoClose: 5000,
    //   });
    //   setCount(1);
    // }
  }, [loginError, count]);


  return (
    <>
      <ToastContainer />
      <TopNavbar />
      <Header />
      <Services />
      <About />
      <Pricing />
      <Contact />
      <Footer />
    </>
  );
}


