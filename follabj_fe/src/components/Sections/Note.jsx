import React, { useEffect } from "react";
import styled from "styled-components";
// Components
import NoteBox from "../Elements/NoteBox";
import FullButton from "../Buttons/FullButton";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { getNotesByUserId } from "../../Redux/note/noteActions";
import { useSelector } from "react-redux";

export default function Note() {

  const dispatch = useDispatch();
  const history = useHistory();

  const user_id = 3

  useEffect(()=>{
    dispatch(getNotesByUserId(user_id))
  },[])
  
  const notes = useSelector((state) => state.note)



  return (
    <Wrapper id="notes">
      <div className="whiteBg">
        <div className="container">
          <HeaderInfo>
            <h1 className="font40 extraBold">All Your Notes</h1>
          </HeaderInfo>
          <div className="row textCenter">

          {notes.map((note)=>{ 
        
           return(
                <div className="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                  <NoteBox
                    title="New Office!"
                    text= {note.content}
                    action={() => alert("clicked")}
                  />
                </div>
          )
          })
          }
          </div>

          <div className="row flexCenter">
            <div style={{ margin: "50px 0", width: "200px" }}>
              <FullButton title="Load More" action={() => alert("clicked")} />
            </div>
          </div>
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