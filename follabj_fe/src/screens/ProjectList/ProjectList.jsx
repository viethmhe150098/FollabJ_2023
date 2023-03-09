import React, { useEffect } from 'react';
// Sections
import PrjTopNavBar from "../../components/Nav/PrjTopNavBar";
import Projects from "./Projects";
import Footer from "../../components/Sections/Footer"

export default function ProjectList() {

    return (
        <>
            <PrjTopNavBar />
            <Projects />
            <Footer />
        </>
    );
}


