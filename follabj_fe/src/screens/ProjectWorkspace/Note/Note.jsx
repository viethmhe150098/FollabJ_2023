import React, { useEffect } from "react";
import styled from "styled-components";
// Components
import NoteBox from "../../../components/Elements/NoteBox";
import FullButton from "../../../components/Buttons/FullButton";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { getNotesByUserId } from "../../../Redux/note/noteActions";
import { useSelector } from "react-redux";
import NoteModal from "../../../components/Modals/NoteModal";
import Popup from "reactjs-popup";

export default function Note() {

  const dispatch = useDispatch();
  const history = useHistory();

  const user_id = useSelector((state) => state.auth.login.currentUser.id)

  const projectId = useSelector((state) => state.project.currentProject.id);

  // const history = useHistory();

  if (projectId == null) {
      // history.push("/projects")
      window.location.href = "/projects";
  }

  useEffect(()=>{
    dispatch(getNotesByUserId(user_id))
  },[])
  
  const notes = useSelector((state) => state.note)

  const handleNote = (note) => {
    history.push({
      pathname: "/noteEdit",
      state: note
    })
  }

  return (
    <Wrapper id="notes">
      <div className="whiteBg">
        <div className="container">
          <HeaderInfo>
            <h1 className="font40 extraBold">All Your Notes</h1>
            <Popup modal trigger={<div style={{
              width: "100px", marginLeft:"50px"
              }}>
                <FullButton title={"Add Note"} /></div>}>
                {close => <NoteModal close={close} />
                }
              </Popup>
          </HeaderInfo>
          <div className="row textCenter">

          {notes.map((note,index)=>{ 
        
           return(
                <div key={index} className="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                  <NoteBox
                    title={note.title}
                    text= {`Created Date: ${note.createdDate}`}
                    action={() => handleNote(note)}
                  />
                </div>
          )
          })
          }
          </div>
          {/* <div className="row flexCenter">
            <div style={{ margin: "50px 0", width: "200px" }}>
              <FullButton title="Load More" action={() => alert("clicked")} />
            </div>
          </div> */}
        </div>
      </div>
   
    </Wrapper>
  );
}

const Wrapper = styled.section`
  width: 100%;
  padding-top: 20px;
`;
const HeaderInfo = styled.div`
  margin-bottom: 30px;
  @media (max-width: 860px) {
    text-align: center;
  }
`;