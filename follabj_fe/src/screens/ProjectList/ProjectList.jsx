import React, { useEffect } from 'react';
// Sections
import PrjTopNavBar from "../../components/Nav/PrjTopNavBar";
import Projects from "../../components/Sections/Projects";
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


