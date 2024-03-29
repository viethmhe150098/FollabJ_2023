import React from "react";

import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { BsFileTextFill } from "react-icons/bs";
import styled from "styled-components";
import { getFiles } from "../../../Redux/file/fileActions";
import FullButton from "../../../components/Buttons/FullButton";
import FileModal from "../../../components/Modals/FileModal";
import Popup from "reactjs-popup";
import { downloadFile } from "../../../Redux/file/fileAPI";
import {
    FaAngleLeft, FaAngleRight
} from "react-icons/fa";

//table libraby
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import "../../Admin/MainDashboard/Table/Table.css";


const FileWorkspace = () => {
    const dispatch = useDispatch();

    const project_id = useSelector((state) => state.project.currentProject.id)
    const page_number = 0;

    useEffect(() => {
        dispatch(getFiles({ project_id, page_number }));
    }, [])
    const files = useSelector((state) => state.file)

    const [currentPage, setCurrentPage] = useState(1);
    const [filesPerPage, setFilesPerPage] = useState(6);
    //Pagination
    const indexOfLastFile = currentPage * filesPerPage;
    const indexOfFirstFile = indexOfLastFile - filesPerPage;
    const currentFiles = files.slice(indexOfFirstFile, indexOfLastFile);

    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
    };
    const handleDownload = (file_id, fileName) => {
        downloadFile(project_id, file_id)
            .then((response) => {
                // console.log(response)
                // create file link in browser's memory
                const href = URL.createObjectURL(response.data);

                // create "a" HTML element with href to file & click
                const link = document.createElement('a');
                link.href = href;
                link.setAttribute('download', fileName); //or any other extension
                document.body.appendChild(link);
                link.click();

                // clean up "a" element & remove ObjectURL
                document.body.removeChild(link);
                URL.revokeObjectURL(href);
            }).catch((error) => {
                console.log(error)
            })
    }

    const makeStyle = (status) => {
        if (status === 'Download') {
            return {
                background: 'rgb(145 254 159 / 47%)',
                color: 'green',
                cursor: 'pointer'
            }
        }
        else if (status === 'Delete') {
            return {
                background: '#ffadad8f',
                color: 'red',
                cursor: 'pointer'

            }
        }
    }
    return (
        <>
            <HeaderInfo>
                <h1 className="font30 extraBold">Files</h1>
            </HeaderInfo>
            <Popup modal trigger={<div style={{
                width: "150px"
            }}>
                <FullButton title={"Upload File"} /></div>}>
                {close => <FileModal close={close} />}
            </Popup>
            {files.length === 0 ?
                <EmptyMessage>No files found. Please try uploading some files to get started.</EmptyMessage> :

                <div className="Table">
                    <TableContainer
                        component={Paper}
                        style={{ boxShadow: "0px 13px 20px 10px #80808029", minHeight: '381px' }}
                    >
                        <Table sx={{ minWidth: 650 }} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>File Name</TableCell>
                                    <TableCell align="left">Uploaded Date</TableCell>
                                    <TableCell align="left">Uploader</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody style={{ color: "white" }}>
                                {currentFiles.map((item, index) => (
                                    <TableRow key={index}>
                                        <TableCell component="th" scope="row">
                                            {item.fileName}
                                        </TableCell>
                                        <TableCell align="left">{item.uploadDate}</TableCell>
                                        <TableCell align="left">
                                            <span className="Details" >{item.user.username}</span>
                                        </TableCell>
                                        <TableCell align="left">
                                            <button onClick={() => { handleDownload(item.id, item.fileName) }} className="status" style={makeStyle('Download')}>Download</button>
                                        </TableCell>
                                        <TableCell align="left">
                                            <button className="status" style={makeStyle('Delete')}>Delete</button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <div className="pagination-wrapper">
                        {files.length > filesPerPage && (
                            <Pagination
                                filesPerPage={filesPerPage}
                                totalFiles={files.length}
                                paginate={paginate}
                                currentPage={currentPage}
                            />
                        )}
                    </div>
                </div>
            }
        </>);
}
function Pagination({ filesPerPage, totalFiles, paginate, currentPage }) {
    const pageNumbers = [];

    for (let i = 1; i <= Math.ceil(totalFiles / filesPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <div className="pagination">
          <a href="#" className="page-link"> <FaAngleLeft/> &nbsp; </a>
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
          <a href="#" className="page-link"> <FaAngleRight/> &nbsp; </a>

        </div>
    );
}
const EmptyMessage = styled.div`
  text-align: center;
  margin: 50px auto;
  font-size: 24px;
  font-weight: bold;
  color: #888;
`;
const HeaderInfo = styled.div`
margin-bottom: 30px;
margin-left: 5px;
@media (max-width: 860px) {
  text-align: center;
}
`
export default FileWorkspace