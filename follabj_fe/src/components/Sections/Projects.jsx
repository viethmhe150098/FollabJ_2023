
import styled from "styled-components";
// Components
import ProjectBox from "../Elements/ProjectBox";
import FullButton from "../Buttons/FullButton";
// Assets
import ProjectImg1 from "../../assets/img/projects/1.png";

import { useDispatch } from "react-redux";
import { setCurrentProjectId, setCurrentProjectUserRole } from "../../Redux/project/projectSlice";
import { getProjectMembersByProjectId, getProjectsByUserId } from "../../Redux/project/projectActions";
import { useHistory } from "react-router-dom";
import { useSelector } from "react-redux";
import { useEffect } from "react";

const Projects = () => {

  const dispatch = useDispatch();
  const history = useHistory();

  const user_id = 3

  useEffect(() => {
      dispatch(getProjectsByUserId(user_id))
  }, [])

  const projects = useSelector((state) => state.project.projects.allProjects)


  const setCurrentProject = (project_id) => {

      dispatch(setCurrentProjectId(project_id));
      
      dispatch(getProjectMembersByProjectId(project_id));

      // if (user_id == members[0].id) {
      //   dispatch(setCurrentProjectUserRole("LEADER"))
      // } else {
      //   dispatch(setCurrentProjectUserRole("ACTIVE_USER"))
      // }

      history.push("/tasks")
  }

  return (

    <Wrapper id="projects" className="lightBg" style={{paddingBottom:"120px"}}>
      <div >
        <div className="container" style={{ paddingTop: "150px" }}>
          <HeaderInfo>
            <h1 className="font30 extraBold textCenter">My Projects</h1>
          </HeaderInfo>
          <div className="row textCenter">
            {
              projects.map((project) => 
              <div className="col-xs-12 col-sm-4 col-md-4 col-lg-4" key={project.id.toString()}>
                <ProjectBox
                  img={ProjectImg1}
                  title={project.name}    
                  action={() => {setCurrentProject(project.id)}}
                />
              </div>)
            }
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

export default Projects
