import React from "react";

import { useEffect, useState } from "react";
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

    const users = useSelector((state) => state.user)
    const [currentPage, setCurrentPage] = useState(1);
    const [usersPerPage, setUsersPerPage] = useState(10);

    const indexOfLastUser = currentPage * usersPerPage;
    const indexOfFirstUser = indexOfLastUser - usersPerPage;
    const currentUsers = users.slice(indexOfFirstUser, indexOfLastUser);
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
    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
    };
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
                            {currentUsers.map((user, index) => (
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
                <div className="pagination-wrapper">
                    {users.length > usersPerPage && (
                        <Pagination
                            usersPerPage={usersPerPage}
                            totalUsers={users.length}
                            paginate={paginate}
                            currentPage={currentPage}
                        />
                    )}
                </div>
            </div>


        </>);
}

function Pagination({ usersPerPage, totalUsers, paginate, currentPage }) {
    const pageNumbers = [];

    for (let i = 1; i <= Math.ceil(totalUsers / usersPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <div className="pagination">
            {pageNumbers.map((number) => (
                <div
                    key={number}
                    className={`page-item${currentPage === number ? " active" : ""}`}
                    onClick={() => paginate(number)}
                >
                    <a href="#" className="page-link">
                        {number}
                    </a>
                </div>
            ))}
        </div>
    );
}

export default Users