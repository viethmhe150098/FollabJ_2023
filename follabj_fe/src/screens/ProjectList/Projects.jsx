
import styled from "styled-components";
// Components
import ProjectBox from "../../components/Elements/ProjectBox";
// Assets
import ProjectImg1 from "../../assets/img/projects/1.png";
import { useMediaQuery } from '@material-ui/core';
import FullButton from '../../components/Buttons/FullButton';
import SendLeaderRequestModal from '../../components/Modals/SendLeaderRequest';
import Content from '../../components/Modals/CreateProject';
import { useDispatch } from "react-redux";
import { setCurrentProjectDescription, setCurrentProjectId, setCurrentProjectLeader, setCurrentProjectName, setCurrentProjectUserRole } from "../../Redux/project/projectSlice";
import { getProjectMembersByProjectId, getProjectsByUserId } from "../../Redux/project/projectActions";
import { useHistory } from "react-router-dom";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import {
  FaAngleLeft, FaAngleRight
} from "react-icons/fa";
import axios from "axios";
import ViewProjectModal from "../../components/Modals/ViewProject";
import Popup from "reactjs-popup";

const Projects = () => {

  const dispatch = useDispatch();
  const history = useHistory();
  const user_id = useSelector((state) => state.auth.login.currentUser.id)
  const projects = useSelector((state) => state.project.projects.allProjects)
  //const members = useSelector((state) => state.project.currentProject.members)
  const isPhone = useMediaQuery('(max-width:600px)');
  const roles = localStorage.getItem("role_name")
  const [currentPage, setCurrentPage] = useState(1);
  const [projectsPerPage, setProjectsPerPage] = useState(6);

  const indexOfLastPrj = currentPage * projectsPerPage;
  const indexOfFirstPrj = indexOfLastPrj - projectsPerPage;
  const currentProjects = projects.slice(indexOfFirstPrj, indexOfLastPrj);
  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };
  const [images, setImages] = useState([])

  const fetchImages = async () => {
    const images = new Array(projects.length)

    const imageRequests =
      new Array(projects.length)
        .fill("https://random.imagecdn.app/500/500")

    imageRequests.map(async (request) => {
      const response = await axios.get(request)
      images.push(response.request.responseURL)
    })

    setImages(images)
  }

  useEffect(() => {

    dispatch(getProjectsByUserId(user_id))

    fetchImages()

    // console.log(images)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const openProjectModal = (project) => {
    const button = document.getElementById("project_" + project.id)

    button.click()
  }

  const setCurrentProject = (project) => {

    dispatch(setCurrentProjectId(project.id));
    dispatch(setCurrentProjectName(project.name));
    dispatch(setCurrentProjectDescription(project.des));
   
    dispatch(getProjectMembersByProjectId(project.id)).unwrap().then((result) => {
      if (result !== []) {
        if (user_id === result[0].id) {
          dispatch(setCurrentProjectUserRole("LEADER"))
        } else {
          dispatch(setCurrentProjectUserRole("ACTIVE_USER"))
        }
        dispatch(setCurrentProjectLeader(project.leader))
      }
      //console.log(result)
      history.push("/tasks")
    })

  }


  return (

    <Wrapper id="projects" className="lightBg" style={{ paddingBottom: "50px" }}>
      <div >

        <div className="container" style={{ paddingTop: "150px", minHeight: "709px" }}>
          {isPhone && (
            <>
              {roles.includes('LEADER') && (
                <UlWrapperRight className="flexNullCenter">
                  <Popup
                    modal
                    trigger={
                      <li className="semiBold pointer font15 flexCenter">
                        <FullButton title="Create Project" />
                      </li>
                    }
                  >
                    {close => <Content close={close} />}
                  </Popup>
                </UlWrapperRight>
              )}
              {!roles.includes('LEADER') && (
                <UlWrapperRight className="flexNullCenter">
                  <Popup
                    modal
                    trigger={
                      <li>
                        <FullButton title="Send Leader Request" />
                      </li>
                    }
                  >
                    {close => <SendLeaderRequestModal close={close} />}
                  </Popup>
                </UlWrapperRight>
              )}
            </>
          )}
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
                      action={() => { setCurrentProject(project) }}
                      rightClickAction={() => { openProjectModal(project) }}
                    />
                    <Popup modal trigger={<button id={"project_" + project.id}></button>}>
                      {close => <ViewProjectModal close={close} project={project} type={"readonly"} />}
                    </Popup>
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
const UlWrapperRight = styled.ul`

`;
export default Projects
