import React from 'react';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router';
import { deleteMember } from '../../../Redux/project/projectActions';

export default function MemberList() {

    const dispatch  = useDispatch();

    const history = useHistory();

    const members = useSelector((state) => state.project.currentProject.members)
    //console.log(members)
    
    const user_role = useSelector((state) => state.project.currentProject.userRole)

    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const project_id = useSelector((state) => state.project.currentProject.id);

    const handleDeleteMember = (member_id) => {
        dispatch(deleteMember({project_id, member_id}))
    }

    return (
        <>
            <h1>Project Members: </h1>
            {members != null && members.map((member, index) => 
                <div key={index}>
                    <h3 style={{display: "inline-block"}} className='font20'>{member.username} </h3>
                    {user_role=="LEADER" && user_id != member.id && <button onClick={()=>handleDeleteMember(member.id)} className='redBg font20 radius6 lightColor tag'>Delete</button>}
                </div>
           )}
        </>
    );
}


