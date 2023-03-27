import React from 'react';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router';
import Popup from 'reactjs-popup';
import LeaveGroupModal from '../../../components/Modals/LeaveGroup';
import InviteUser from '../../../components/Sections/inviteUser/InviteUser';
import { leaveProject } from '../../../Redux/project/projectActions';
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
    const makeStyle = (status) => {
        if (status === 'Member') {
            return {
                background: 'rgb(145 254 159 / 47%)',
                color: 'green',
            }
        }
        else if (status === 'Leave') {
            return {
                background: '#ffadad8f',
                color: 'red',
                marginTop: '50px',
                height: '50px',
                '&:hover': {
                    background: 'red',
                    color: 'white',
                },
            }
        } else {
            return {
                background: '#59bfff',
                color: 'white',
            }
        }
    }

    return (
        <>
            {user_role == "LEADER" && <InviteUser />}
            <div className="invite-user-container">
                <MemberList />
                {user_role == "LEADER" &&
                    <Popup modal trigger={<button className='status' style={makeStyle('Leave')}>Leave project</button>}>
                        {close => <LeaveGroupModal close={close} />}
                    </Popup>
                }
                {user_role != "LEADER" &&
                    <button onClick={() => { handleMemberLeave() }} className='status' style={makeStyle('Leave')}>Leave project</button>
                }
            </div>
        </>
    );


}


