import React from 'react';
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import { acceptInvitation, getInvitationsByUserId } from '../../Redux/invitation/invitationActions';
//table libraby
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import "../../screens/Admin/MainDashboard/Table/Table.css";
const Invitations = () => {

    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const user_invitations = useSelector((state) => state.invitation.user_invitations)

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getInvitationsByUserId(user_id))
    }, [])

    const handleAccept = (invitation) => {
        dispatch(acceptInvitation({ user_id, invitation }))
    }
    const makeStyle = (status) => {
        if (status === 'Accept') {
            return {
                background: 'rgb(145 254 159 / 47%)',
                color: 'green',
                cursor: 'pointer',
                marginRight: '30px'
            }
        }
        else if (status === 'Decline') {
            return {
                background: '#ffadad8f',
                color: 'red',
                cursor: 'pointer',
                marginRight: '50px'
            }
        }
    }

    return (

        <div className="container">

            <h1>Invitations</h1>
            {user_invitations.length === 0 ?
                <EmptyMessage>No invitations found!</EmptyMessage> :
                <div className="Table">
                    <TableContainer
                        component={Paper}
                        style={{ boxShadow: "0px 13px 20px 10px #80808029", marginBottom: '50px' }}
                    >

                        <Table sx={{ minWidth: 650 }} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>Project Name</TableCell>
                                    <TableCell align="left">Project Leader's Mail</TableCell>

                                </TableRow>
                            </TableHead>
                            <TableBody style={{ color: "white" }}>
                                {user_invitations.map((invitation, index) => (
                                    <TableRow key={index}>
                                        <TableCell component="th" scope="row">
                                            {invitation.project.name}
                                        </TableCell>
                                        <TableCell component="th" scope="row">
                                            {invitation.email}
                                        </TableCell>
                                        <TableCell align="right">
                                            <button onClick={() => handleAccept(invitation)} className="status" style={makeStyle('Accept')}>Accept</button>
                                            <button className="status" style={makeStyle('Decline')}>Decline</button>
                                        </TableCell>

                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </div>
            }
        </div>
    )
}

const EmptyMessage = styled.div`
  text-align: center;
  margin: 50px auto;
  font-size: 24px;
  font-weight: bold;
  color: #888;
`;
export default Invitations;