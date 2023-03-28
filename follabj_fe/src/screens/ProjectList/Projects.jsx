
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
import { useEffect, useState } from "react";
import {
  FaAngleLeft, FaAngleRight
} from "react-icons/fa";

const Projects = () => {

  const dispatch = useDispatch();
  const history = useHistory();

  const user_id = useSelector((state) => state.auth.login.currentUser.id)

  useEffect(() => {
    dispatch(getProjectsByUserId(user_id))
  }, [])

  const projects = useSelector((state) => state.project.projects.allProjects)

  const members = useSelector((state) => state.project.currentProject.members)

  const [currentPage, setCurrentPage] = useState(1);
  const [projectsPerPage, setProjectsPerPage] = useState(6);

  const indexOfLastPrj = currentPage * projectsPerPage;
  const indexOfFirstPrj = indexOfLastPrj - projectsPerPage;
  const currentProjects = projects.slice(indexOfFirstPrj, indexOfLastPrj);
  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };
  const setCurrentProject = (project_id) => {

    dispatch(setCurrentProjectId(project_id));

    dispatch(getProjectMembersByProjectId(project_id)).unwrap().then((result) => {
      if (result != []) {
        if (user_id == result[0].id) {
          dispatch(setCurrentProjectUserRole("LEADER"))
        } else {
          dispatch(setCurrentProjectUserRole("ACTIVE_USER"))
        }
      }
      //console.log(result)
      history.push("/tasks")
    })

  }




  return (

    <Wrapper id="projects" className="lightBg" style={{ paddingBottom: "50px" }}>
      <div >

        <div className="container" style={{ paddingTop: "150px", minHeight: "709px" }}>
          <HeaderInfo>
            <h1 className="font30 extraBold textCenter">My Projects</h1>
          </HeaderInfo>
          {projects.length === 0 ?
            <EmptyMessage>No projects found!</EmptyMessage> :
            <div className="row textCenter">
              {
                currentProjects.map((project) =>
                  <div className="col-xs-12 col-sm-4 col-md-4 col-lg-4" key={project.id.toString()}>
                    <ProjectBox
                      img={ProjectImg1}
                      title={project.name}
                      action={() => { setCurrentProject(project.id) }}
                    />
                  </div>)
              }
            </div>
          }
        </div>

      </div>
      <div className="pagination-wrapper">
        {projects.length > projectsPerPage && (
          <Pagination
            projectsPerPage={projectsPerPage}
            totalProjects={projects.length}
            paginate={paginate}
            currentPage={currentPage}
          />
        )}
      </div>
    </Wrapper>
  );
}
function Pagination({ projectsPerPage, totalProjects, paginate, currentPage }) {
  const pageNumbers = [];

  for (let i = 1; i <= Math.ceil(totalProjects / projectsPerPage); i++) {
    pageNumbers.push(i);
  }

  return (
    <div className="pagination">
      <a href="#" className="page-link"> <FaAngleLeft /> &nbsp; </a>

      {pageNumbers.map((number) => (
        <div
          key={number}
          className={`page-item${currentPage === number ? " active" : ""}`}
          onClick={() => paginate(number)}>
          <a href="#" className="page-link">
            {number}
          </a>
        </div>
      ))}
      <a href="#" className="page-link"> <FaAngleRight /> &nbsp; </a>
    </div>
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
const EmptyMessage = styled.div`
  text-align: center;
  margin: 50px auto;
  font-size: 24px;
  font-weight: bold;
  color: #888;
`;
export default Projects
