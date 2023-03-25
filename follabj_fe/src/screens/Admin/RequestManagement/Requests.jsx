import React from "react";

import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import styled from "styled-components";
import { acceptRequest, getRequests } from "../../../Redux/leaderRequest/requestActions";

const Requests = () => {
    const dispatch = useDispatch();

    const page_number = 0;

    const leaderRequests = useSelector((state) => state.leaderRequest)

    useEffect(() => {
        dispatch(getRequests(page_number));
    }, [])

    const handleAccept = (request) => {
        dispatch(acceptRequest(request.id))
        //console.log("accepeted")
    }

    return (
        <>
        <div className="container">
            <div className="row ">
                <div className="col-lg-8 font20">Username</div>
                <div className="col-lg-4 font20">Request Options</div>
            </div>
            { leaderRequests.map((request, index) =>
                <Row className="row " key={index}>
                <Name className="col-lg-8">{request.user_fullname}</Name>
                <div className="col-lg-2">
                    <button onClick={() => handleAccept(request)} className='greenBg font20 radius6 lightColor tag'>Accept</button>
                </div>
            </Row>            
            )
            }   
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
        </div>
        </> );
}

const Row = styled.div`
    border: 2px solid black;
    &:hover: {
        background-color: #E5E5E5;
    }
`

const Name = styled.p`
    font-size:20px;
    display: inline-block;
`
export default Requests