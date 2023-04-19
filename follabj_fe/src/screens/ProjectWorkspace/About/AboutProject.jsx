import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router';
import Popup from 'reactjs-popup';
import LeaveGroupModal from '../../../components/Modals/LeaveGroup';
import { leaveProject } from '../../../Redux/project/projectActions';
import InviteUser from './InviteUser';
import MemberList from './MemberList';
import styled from 'styled-components';
import ViewProjectModal from '../../../components/Modals/ViewProject';

// Sections
export default function AboutProject() {

    const dispatch = useDispatch();

    const history = useHistory();

    const members = useSelector((state) => state.project.currentProject.members)
    //console.log(members)
    const user_role = useSelector((state) => state.project.currentProject.userRole)

    const user_id = useSelector((state) => state.auth.login.currentUser.id)
    const project = useSelector((state) => state.project.currentProject);
    const project_id = useSelector((state) => state.project.currentProject.id);
    const project_name = useSelector((state) => state.project.currentProject.name);
    const project_des = useSelector((state) => state.project.currentProject.des);

    const handleMemberLeave = () => {
        dispatch(leaveProject({
            project_id,
            user_id
        }))

        history.push("/projects")
    }

    return (
        <>
            <Popup modal trigger={
                <Container>
                    <Content className='animate'>Project Name: <span style={{ fontWeight: "normal" }}>{project_name}</span> </Content>
                    <Content className='animate'>Project Description: <span style={{ fontWeight: "normal" }}>{project_des}</span></Content>
                </Container>
            }>
                {close => <ViewProjectModal close={close} project={project} type={"readonly"} />}
            </Popup>

            {user_role == "LEADER" && <InviteUser />}
            <div className="invite-user-container">
                <MemberList />
                {user_role == "LEADER" && members.length > 1 && (
                    <div style={{ width: '300px', height: '200px' }}>
                        <Popup modal trigger={
                            <button
                                className='status red-btn animate font18 extraBold '
                                style={{ width: '60%', height: '30%', marginTop: '50px' }}>Leave project</button>}>
                            {close => <LeaveGroupModal close={close} />}
                        </Popup>
                    </div>)
                }
                {user_role == "LEADER" && (
                    <div className='extraBold ' style={{ marginTop: '80px', color: 'red', fontSize: '14px' }}>
                        <p>You cannot leave the project at the moment as you are the only member in the project </p>
                    </div>)
                }


                {user_role != "LEADER" &&
                    <button onClick={() => { handleMemberLeave() }} style={{ width: '10%', height: '50px', marginTop: '50px' }} className='status red-btn animate font18 extraBold'>Leave project</button>
                }
            </div>
        </>
    );


}

const Container = styled.div`
  margin: 20px;
  cursor: pointer;
`;
const Content = styled.h2`
  font-size: 1.17em;
  font-weight: normal;
  color: #333;
  line-height: 1.5;
  font-weight: bold;
  margin-bottom: 20px;
  &:hover {
    color: #f58025; 
  }
`;

