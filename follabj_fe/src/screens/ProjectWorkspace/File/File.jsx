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
        <Popup modal trigger={<div style={{
          width: "150px", marginLeft:"50px"
        }}>
          <FullButton title={"Upload File"} /></div>}>
                    {close => <FileModal close={close} />}
        </Popup>
        <div className="container">
            
            <div className="row ">
                {
                    files.map((item, index) => {return (
                    <FileGrid className="col-lg-4 col-md-6 col-xs-12" key={index} onDoubleClick={()=>handleDownload(item.id,item.fileName)}>
                        
                        <FileIcon/>
                    
                        <FileName>{item.fileName}</FileName>

                    </FileGrid>
                    )})
                }
            </div>
        </div>
        </> );
}

const FileGrid = styled.div`
    border: 2px solid black;
    border-radius : 0.5rem;
    margin-right: 5px;
    max-width: 243px;
    &:hover: {
        background-color: #E5E5E5;
    }
`

const FileIcon = styled(BsFileTextFill)`
    width:50%;
    height: 75%;
    display: block;
    margin-right:auto;
    margin-left:auto
`

const FileName = styled.p`
    display: block;
    text-align:center;
    overflow:hidden;
    text-overflow:ellipsis;
    white-space:nowrap
`
export default FileWorkspace