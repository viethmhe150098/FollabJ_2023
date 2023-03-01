
import styled from "styled-components";
// Components
import ProjectBox from "../Elements/ProjectBox";
import FullButton from "../Buttons/FullButton";
// Assets
import ProjectImg1 from "../../assets/img/projects/1.png";
import ProjectImg2 from "../../assets/img/projects/2.png";
import ProjectImg3 from "../../assets/img/projects/3.png";
import ProjectImg4 from "../../assets/img/projects/4.png";
import ProjectImg5 from "../../assets/img/projects/5.png";
import ProjectImg6 from "../../assets/img/projects/6.png";
// import AboutImg from "../../assets/img/add/about.png";

import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { getAllProjectsByUserId } from "../../Redux/project/projectAPI";


export default function Projects() {
  
  const [projects, setProjects] = useState("");
  const access_token = localStorage.getItem("access_token");

  const dispatch = useDispatch();

  useEffect(() => {
    const fetchData = async () => {
      const response = await getAllProjectsByUserId(3,access_token,dispatch);
      setProjects(response.data);
    };
    fetchData();
  }, []);

  return (
    <Wrapper id="projects" className="lightBg" style={{ paddingBottom: "120px" }}>
      <div>
        <div className="container" style={{ paddingTop: "150px" }}>
          <HeaderInfo>
            <h1 className="font30 extraBold textCenter">My Projects</h1>
          </HeaderInfo>
          <div className="row textCenter">
            {projects.length > 0 && projects.map(project => (
              <div key={project.id} className="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                <ProjectBox
                  img={project.image}
                  title={project.p_name}
                  //action={() => alert("clicked")}
                />
              </div>
            ))}
          </div>
          <div className="row flexCenter">
            <div style={{ margin: "50px 0", width: "200px" }}>
              <FullButton title="Load More" action={() => alert("clicked")} />
            </div>
          </div>
        </div>
      </div>
    </Wrapper>
  );
}

const Wrapper = styled.section`
  width: 100%;
`;
const HeaderInfo = styled.div`
margin-bottom: 40px;
  @media (max-width: 860px) {
    text-align: center;
  }
`;


