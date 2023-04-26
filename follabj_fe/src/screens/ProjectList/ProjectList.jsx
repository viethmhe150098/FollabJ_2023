import React from 'react';

// Sections
import PrjTopNavBar from "../../components/Nav/PrjTopNavBar";
import Projects from "./Projects";
import Footer from "../../components/Sections/Footer"
import Invitations from './Invitations';


export default function ProjectList() {

    return (
        <>
            <PrjTopNavBar />
            <Projects />
            <Invitations />



            <Footer />
        </>
    );
}


