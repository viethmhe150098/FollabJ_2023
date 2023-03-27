import React from "react";

import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { acceptRequest, getRequests } from "../../../Redux/leaderRequest/requestActions";

//table libraby
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import "../../Admin/MainDashboard/Table/Table.css";
const Requests = () => {
    const dispatch = useDispatch();

    const page_number = 0;

    const leaderRequests = useSelector((state) => state.leaderRequest)

    useEffect(() => {
        dispatch(getRequests(page_number));
    }, [])

    const handleAccept = (request) => {
        dispatch(acceptRequest(request))
        //console.log("accepeted")
    }
    const makeStyle = (status) => {
        if (status === 'Accept') {
            return {
                background: 'rgb(145 254 159 / 47%)',
                color: 'green',
                cursor: 'pointer',
                minWidth: '10%',
                width: '83px',
            }
        }
        else if (status === 'Decline') {
            return {
                background: '#ffadad8f',
                color: 'red',
                cursor: 'pointer',
                width: '83px',
                minWidth: '10%'

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
                            {leaderRequests.map((item, index) => (
                                <TableRow key={index}>
                                    <TableCell component="th" scope="row">
                                        {item.user_fullname}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {item.email}
                                    </TableCell>

                                    <TableCell align="left">
                                        <button onClick={() => handleAccept(item)} className="status" style={makeStyle('Accept')}>Accept</button>

                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </>);
}
export default Requests