import React from 'react';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router';
import { deleteMember } from '../../../Redux/project/projectActions';
//table libraby
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import "../../Admin/MainDashboard/Table/Table.css";

export default function MemberList() {

    const dispatch = useDispatch();

    const history = useHistory();

    const members = useSelector((state) => state.project.currentProject.members)
    //console.log(members)

    const user_role = useSelector((state) => state.project.currentProject.userRole)

    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const project_id = useSelector((state) => state.project.currentProject.id);

    const handleDeleteMember = (member_id) => {
        dispatch(deleteMember({ project_id, member_id }))
    }
    const makeStyle = (status) => {
        if (status === 'Member') {
            return {
                background: 'rgb(145 254 159 / 47%)',
                color: 'green',
            }
        }
       else {
            return {
                background: '#59bfff',
                color: 'white',
            }
        }
    }
    return (
        <>
            <div className="Table">
                <h3 style={{ marginBottom: '30px' }}>Projects List</h3>
                <TableContainer
                    component={Paper}
                    style={{ boxShadow: "0px 13px 20px 10px #80808029", minHeight: '381px' }}
                >
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>User Name</TableCell>
                                <TableCell align="left">Role</TableCell>
                                <TableCell align="left">Option</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody style={{ color: "white" }}>
                            {members.map((item, index) => (
                                <TableRow key={index}>
                                    <TableCell>
                                        {item.username}
                                    </TableCell>
                                    <TableCell align="left">
                                        {index === 0 ? <button className="status" style={makeStyle('Leader')}>Leader</button> : <button className="status" style={makeStyle('Member')}>Team member</button>}
                                    </TableCell>
                                    <TableCell align="left">
                                        {user_role == "LEADER" && user_id != item.id && <button onClick={() => handleDeleteMember(item.id)} className="status red-btn animate" >Delete</button>}
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </>
    );
}



