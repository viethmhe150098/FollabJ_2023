import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import "./Table.css";
import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { fetchAllProjects } from "../../../../Redux/project/projectActions"

const makeStyle = (status) => {
  if (status === 'Approved') {
    return {
      background: 'rgb(145 254 159 / 47%)',
      color: 'green',
    }
  }
  else if (status === 'Pending') {
    return {
      background: '#ffadad8f',
      color: 'red',
    }
  }
  else {
    return {
      background: '#59bfff',
      color: 'white',
    }
  }
}

export default function BasicTable() {
  const dispatch = useDispatch();
  const projects = useSelector((state) => state.project.projects.allProjects)
  useEffect(() => {
    dispatch(fetchAllProjects());
  }, [])

  return (
    <div className="Table">
      <h3 style={{ marginBottom: '30px' }}>Projects List</h3>
      <TableContainer
        component={Paper}
        style={{ boxShadow: "0px 13px 20px 10px #80808029" }}
      >
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Project Name</TableCell>
              <TableCell align="left">Leader Email</TableCell>
              <TableCell align="left">Date</TableCell>
              <TableCell align="left">Status</TableCell>
              <TableCell align="left"></TableCell>
            </TableRow>
          </TableHead>
          <TableBody style={{ color: "white" }}>
            {projects.map((row) => (
              <TableRow
                key={row.name}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {row.name}
                </TableCell>
                <TableCell align="left">{row.members[0].email}</TableCell>
                <TableCell align="left">{row.createdDate}</TableCell>
                <TableCell align="left">
                  <button className="status" style={makeStyle(row.status)}>{row.status}</button>
                </TableCell>
                <TableCell align="left" className="Details">Details</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}
