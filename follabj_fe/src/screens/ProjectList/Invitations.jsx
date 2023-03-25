import React from 'react';
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import { acceptInvitation, getInvitationsByUserId } from '../../Redux/invitation/invitationActions';

const Invitations = () => {

    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const user_invitations = useSelector((state)=> state.invitation.user_invitations)

    const dispatch = useDispatch();

    useEffect(()=>{
        dispatch(getInvitationsByUserId(user_id))
    },[])

    const handleAccept = (invitation) => {
        dispatch(acceptInvitation({user_id, invitation}))
    }

    return (
    <div className="container">
        <h1>Invitations</h1>
            <div className="row ">
                <div className="col-lg-8 font20">Project Name</div>
                <div className="col-lg-4 font20">Options</div>
            </div>
            {user_invitations.map((invitation, index) => 
            <Row key={index} className="row ">
                <Name className="col-lg-8 font20">{invitation.project.name}</Name>
                <div className="col-lg-2">
                    <button onClick={() => handleAccept(invitation)} className='greenBg font20 radius6 lightColor tag'>Accep</button>
                </div>
            </Row>
            )
            }
    </div>
    )
}

const Row = styled.div`
    border: 2px solid black;
    &:hover: {
        background-color: #E5E5E5;
    }
`

const Name = styled.p`
    display: inline-block;
    overflow:hidden;
    text-overflow:ellipsis;
    white-space:nowrap
`

export default Invitations;