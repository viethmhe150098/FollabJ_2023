
import styled from "styled-components";
// Components
import ProjectBox from "../../components/Elements/ProjectBox";
import FullButton from "../../components/Buttons/FullButton";
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

  const user_id = useSelector((state) => state.auth.login.currentUser.id)

  useEffect(() => {
      dispatch(getProjectsByUserId(user_id))
  }, [])

  const projects = useSelector((state) => state.project.projects.allProjects)

  const roles = localStorage.getItem("role_name")

  const members = useSelector((state) => state.project.currentProject.members)

  const setCurrentProject = (project_id) => {

      dispatch(setCurrentProjectId(project_id));
      
      dispatch(getProjectMembersByProjectId(project_id));

      while(true) {
        if (members != []) {
          if (user_id == members[0].id) {
            dispatch(setCurrentProjectUserRole("LEADER"))
          } else {
            dispatch(setCurrentProjectUserRole("ACTIVE_USER"))
          }
          break
        }
      }

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
