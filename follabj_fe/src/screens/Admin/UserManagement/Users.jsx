import React from "react";

import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { banUser, getUsers, unbanUser } from "../../../Redux/user/userActions";
//table libraby
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import "../../Admin/MainDashboard/Table/Table.css";
const Users = () => {
    const dispatch = useDispatch();

    const page_number = 0;

    const users = useSelector((state) => state.user)

    useEffect(() => {
        dispatch(getUsers());
        //console.log("dispatched")
    }, [])

    const handleBan = (user) => {
        dispatch(banUser(user))
        //console.log("accepeted")
    }

    const handleUnban = (user) => {
        dispatch(unbanUser(user))
        //console.log("accepeted")
    }
    const makeStyle = (status) => {
        if (status === 'Unban') {
            return {
                background: 'rgb(145 254 159 / 47%)',
                color: 'green',
                cursor: 'pointer',
            }
        }
        else if (status === 'Ban') {
            return {
                background: '#ffadad8f',
                color: 'red',
                cursor: 'pointer',
            }
        }
    }
    return (
        <>
            <div className="Table">
                <TableContainer
                    component={Paper}
                    style={{ boxShadow: "0px 13px 20px 10px #80808029" }}
                >
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>User Name</TableCell>
                                <TableCell align="left">User Mail</TableCell>
                                <TableCell align="left">User options</TableCell>

                            </TableRow>
                        </TableHead>
                        <TableBody style={{ color: "white" }}>
                            {users.map((user, index) => (
                                <TableRow key={index}>
                                    <TableCell component="th" scope="row">
                                        {user.username}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {user.email}
                                    </TableCell>

                                    <TableCell align="left">

                                        {user.status == 1 &&
                                            <button onClick={() => handleBan(user)} className="status" style={makeStyle('Ban')}>Ban User</button>
                                        }
                                        {user.status == 2 &&
                                            <button onClick={() => handleUnban(user)} className="status" style={makeStyle('Unban')}>Unban User</button>
                                        }
                                        {user.status == 0 &&
                                            <button className="status" style={makeStyle('Verify')}>Verifing</button>
                                        }
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>

            {/* {
            files.map((item, index) => {return (
            <FileGrid className="row " key={index}>
                <div className="col-lg-6">
                    <FileIcon/>
                    <FileName className="col-lg-10">{item.fileName}</FileName>
                </div>
                <div className="col-lg-3">{item.uploadDate}</div>
                <div className="col-lg-3">{item.user.username}</div>
            </FileGrid>
            )})} */}

        </>);
}

export default Users