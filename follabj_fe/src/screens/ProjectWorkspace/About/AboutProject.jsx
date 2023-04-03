import React from 'react';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router';
import Popup from 'reactjs-popup';
import LeaveGroupModal from '../../../components/Modals/LeaveGroup';
import { leaveProject } from '../../../Redux/project/projectActions';
import InviteUser from './InviteUser';
import MemberList from './MemberList';

// Sections
export default function AboutProject() {

    const dispatch = useDispatch();

    const history = useHistory();

    const members = useSelector((state) => state.project.currentProject.members)
    //console.log(members)
    const user_role = useSelector((state) => state.project.currentProject.userRole)

    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const project_id = useSelector((state) => state.project.currentProject.id);

    const handleMemberLeave = () => {
        dispatch(leaveProject({
            project_id,
            user_id
        }))

        history.push("/projects")
    }

    return (
        <>
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
                    <div className='extraBold ' style={{marginTop: '80px', color:'red', fontSize:'14px'}}>
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


