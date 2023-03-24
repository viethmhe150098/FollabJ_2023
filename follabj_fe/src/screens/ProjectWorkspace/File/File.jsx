import React from "react";

import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { BsFileTextFill } from "react-icons/bs";
import styled from "styled-components";
import { getFiles } from "../../../Redux/file/fileActions";
import FullButton from "../../../components/Buttons/FullButton";
import FileModal from "../../../components/Modals/FileModal";
import Popup from "reactjs-popup";
import { downloadFile } from "../../../Redux/file/fileAPI";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const FileWorkspace = () => {
    const dispatch = useDispatch();

    const project_id = useSelector((state)=> state.project.currentProject.id)
    const page_number = 0;

    useEffect(() => {
        dispatch(getFiles({project_id, page_number}));
    }, [])

    const files = useSelector((state) => state.file)

    const handleDownload = (file_id, fileName) => {
        downloadFile(project_id,file_id)
            .then ((response) => {
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
            }).catch ((error) => {
                console.log(error)
            })
    }

    // return (
    //     <>
    //     <Popup modal trigger={<div style={{
    //       width: "100px", marginLeft:"50px"
    //     }}>
    //       <FullButton title={"Upload File"} /></div>}>
    //                 {close => <FileModal close={close} />}
    //     </Popup>
    //     <div className="container">
            
    //         <FileContainer>
    //             {
    //                 files.map((item, index) => {return (
    //                 <FileGrid key={index} onDoubleClick={()=>handleDownload(item.id,item.fileName)}>
                        
    //                     <FileIcon/>
                    
    //                     <FileName>{item.fileName}</FileName>

    //                 </FileGrid>
    //                 )})
    //             }
    //         </FileContainer>
    //     </div>
    //     </> );
    return (
        <>
        <ToastContainer/>
        <Popup modal trigger={<div style={{
          width: "150px", marginLeft:"50px"
        }}>
          <FullButton title={"Upload File"} /></div>}>
                    {close => <FileModal close={close} />}
        </Popup>
        <div className="container">
            
            <div className="row ">
                {/* {
                    files.map((item, index) => {return (
                    <FileGrid className="col-lg-4 col-md-6 col-xs-12" key={index} onDoubleClick={()=>handleDownload(item.id,item.fileName)}>
                        
                        <FileIcon/>
                    
                        <FileName>{item.fileName}</FileName>

                    </FileGrid>
                    )})
                } */}
                <div className="col-lg-6 font20">File Name</div>
                <div className="col-lg-3 font20">Uploaded Date</div>
                <div className="col-lg-3 font20">Uploader</div>
            </div>
            {
            files.map((item, index) => {return (
            <Row className="row " key={index}>
                <div className="col-lg-6">
                    <FileIcon />
                    <FileName className="col-lg-10 col-md-12 font20">{item.fileName}</FileName>
                </div>
                <div className="col-lg-3 font20">{item.uploadDate}</div>
                <div className="col-lg-3 font20">{item.user.username}</div>
            </Row>
            )})}
        </div>
        </> );
}

const Row = styled.div`
    border: 2px solid black;
    &:hover: {
        background-color: #E5E5E5;
    }
`

const FileIcon = styled(BsFileTextFill)`
    height: 75%;
    display: inline-block;
`

const FileName = styled.p`
    display: inline-block;
    text-align:center;
    overflow:hidden;
    text-overflow:ellipsis;
    white-space:nowrap
`
export default FileWorkspace