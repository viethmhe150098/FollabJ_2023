import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { banUser, getUsers, unbanUser } from "../../../Redux/user/userActions";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { toast } from 'react-toastify';
import Popup from "reactjs-popup";
import ConfirmationModal from "../../../components/Modals/ConfirmationModal";

const Users = () => {
    const dispatch = useDispatch();
    const users = useSelector((state) => state.user);
    const [searchQuery, setSearchQuery] = useState("");
    const [filteredUsers, setFilteredUsers] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [usersPerPage, setUsersPerPage] = useState(10);
    const [selectedUser, setSelectedUser] = useState(null);
    const indexOfLastUser = currentPage * usersPerPage;
    const indexOfFirstUser = indexOfLastUser - usersPerPage;
    const currentUsers = users.slice(indexOfFirstUser, indexOfLastUser);
    const handleUserSelect = (user) => {
        setSelectedUser(user);
    };

    const handleSearch = () => {
        if (searchQuery.trim() === "") {
            setFilteredUsers([]);
        } else {
            const results = users.filter((user) =>
                user.email.toLowerCase().includes(searchQuery.toLowerCase())
            );
            if (results.length === 0) {
                toast.error("Not found user with that information!");
            }
            setFilteredUsers(results);
        }
    };

    const handleClear = () => {
        setFilteredUsers([]);
        setSearchQuery("");
    };

    useEffect(() => {
        dispatch(getUsers());
    }, []);

    useEffect(() => {
        setFilteredUsers((prevFilteredUsers) =>
            prevFilteredUsers.map((filteredUser) =>
                users.find((user) => user.email === filteredUser.email)
            )
        );
    }, [users]);

    const handleBan = (user) => {
        dispatch(banUser(user));
        setFilteredUsers((prevFilteredUsers) =>
            prevFilteredUsers.map((filteredUser) =>
                filteredUser.email === user.email
                    ? { ...filteredUser, status: 2 }
                    : filteredUser
            )
        );
        const updatedUser = { ...user, status: 2 };
        setSelectedUser(updatedUser);

    };

    const handleUnban = (user) => {
        dispatch(unbanUser(user));
        setFilteredUsers((prevFilteredUsers) =>
            prevFilteredUsers.map((filteredUser) =>
                filteredUser.email === user.email
                    ? { ...filteredUser, status: 1 }
                    : filteredUser
            )
        );
        const updatedUser = { ...user, status: 1 };
        setSelectedUser(updatedUser);

    };


    const makeStyle = (status) => {
        if (status === "Unban") {
            return {
                background: "rgb(145 254 159 / 47%)",
                color: "green",
                cursor: "pointer",
            };
        } else if (status === "Ban") {
            return {
                background: "#ffadad8f",
                color: "red",
                cursor: "pointer",
            };
        }
    };
    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
    };
    return (
        <>
            <div >
                <h3>Search users by email</h3>
                <div className="input-container">
                    <input
                        id="search"
                        type="text"
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value.trim())}
                    />
                    <button className="search-button semiBold" onClick={handleSearch}>Search</button>
                    <button className="clear-button semiBold" onClick={handleClear}>Clear search</button>

                </div>
            </div>
            {selectedUser && (
                <div>
                    <h3>Selected User: {selectedUser.username} &nbsp; {selectedUser.status == 1 &&
                        <button className="status" style={makeStyle('Unban')}>Active User</button>
                    }
                        {selectedUser.status == 2 &&
                            <button className="status" style={makeStyle('Ban')}>Inactive User</button>
                        }
                        {selectedUser.status == 0 &&
                            <button className="status" style={makeStyle('Verify')}>Verifing</button>
                        }</h3>
                    <p>User ID: {selectedUser.id}</p>
                    <p>Email: {selectedUser.email}</p>
                    <p>Full Name: {selectedUser.fullname === null ?
                        <span style={{ color: 'red' }}>Not set yet</span>
                        : selectedUser.fullname}</p>
                    <p>Phone Number: {selectedUser.phone_number === null ?
                        <span style={{ color: 'red' }}>Not set yet</span>
                        : selectedUser.phone_number}</p>
                </div>
            )}
            <div>
                {(filteredUsers.length > 0 ?
                    <div className="Table">
                        <h3>Searched results:</h3>
                        <TableContainer
                            id="search-table"
                            component={Paper}
                            style={{ boxShadow: "0px 13px 20px 10px #80808029", marginTop: '20px' }}
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
                                    {filteredUsers.map((user, index) => (
                                        <TableRow key={index}>
                                            <TableCell component="th" scope="row">
                                                {user.username}
                                            </TableCell>
                                            <TableCell component="th" scope="row">
                                                {user.email}
                                            </TableCell>
                                            <TableCell align="left">

                                                {user.status == 1 && (
                                                    <Popup modal trigger={
                                                        <button className="status" style={makeStyle('Ban')}>Ban User</button>
                                                    }>
                                                        {close => <ConfirmationModal message="Are you sure you want to ban this user?"
                                                            onConfirm={() => handleBan(user)}
                                                            onCancel={close} />}
                                                    </Popup>)
                                                }
                                                {user.status == 2 && (
                                                    <Popup modal trigger={
                                                        <button className="status" style={makeStyle('Unban')}>Unban User</button>
                                                    }>
                                                        {close => <ConfirmationModal message="Are you sure you want to unban this user?"
                                                            onConfirm={() => handleUnban(user)}
                                                            onCancel={close} />}
                                                    </Popup>)
                                                }
                                                {user.status == 0 &&
                                                    <button className="status" style={makeStyle('Verify')}>Verifing</button>
                                                }
                                            </TableCell>
                                            <TableCell onClick={() => handleUserSelect(user)} align="left" className="Details">Details</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </div>
                    : <></>)
                }
            </div>


            <div className="Table">
                <h3 style={{ marginBottom: '10px' }}>All users</h3>
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

                                        {user.status == 1 && (
                                            <Popup modal trigger={
                                                <button className="status" style={makeStyle('Ban')}>Ban User</button>
                                            }>
                                                {close => <ConfirmationModal message="Are you sure you want to ban this user?"
                                                    onConfirm={() => handleBan(user)}
                                                    onCancel={close} />}
                                            </Popup>)
                                        }
                                        {user.status == 2 && (
                                            <Popup modal trigger={
                                                <button className="status" style={makeStyle('Unban')}>Unban User</button>
                                            }>
                                                {close => <ConfirmationModal message="Are you sure you want to unban this user?"
                                                    onConfirm={() => handleUnban(user)}
                                                    onCancel={close} />}
                                            </Popup>)
                                        }
                                        {user.status == 0 &&
                                            <button className="status" style={makeStyle('Verify')}>Verifing</button>
                                        }
                                    </TableCell>
                                    <TableCell onClick={() => handleUserSelect(user)} align="left" className="Details">Details</TableCell>
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
        </>
    );
};
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

export default Users;
