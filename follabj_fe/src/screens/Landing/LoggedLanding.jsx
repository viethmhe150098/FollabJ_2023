import React from 'react';

// Sections
import TopNavbar from "../../components/Nav/TopNavbar";
import Header from "../../components/Sections/Header";
import Services from "../../components/Sections/Services";
import Pricing from "../../components/Sections/Pricing";
import Contact from "../../components/Sections/Contact";
import Footer from "../../components/Sections/Footer"
import About from "../../components/Sections/About"
export default function LoggedLanding() {


  
  return (
    <>
      <TopNavbar />
      <Header />
      <Services />
      <About/>
      <Pricing />
      <Contact />
      <Footer />
    </>
  );
}


