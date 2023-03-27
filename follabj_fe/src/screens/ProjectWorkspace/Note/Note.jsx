import React, { useState, useEffect } from "react";
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
import '../../../style/pagination.css'
import {
  FaAngleLeft, FaAngleRight
} from "react-icons/fa";

export default function Note() {
  const dispatch = useDispatch();
  const history = useHistory();
  const user_id = useSelector((state) => state.auth.login.currentUser.id);
  const projectId = useSelector((state) => state.project.currentProject.id);
  const [currentPage, setCurrentPage] = useState(1);
  const [notesPerPage, setNotesPerPage] = useState(9);

  if (projectId == null) {
    history.push("/projects");
  }

  const notes = useSelector((state) => state.note);
  const indexOfLastNote = currentPage * notesPerPage;
  const indexOfFirstNote = indexOfLastNote - notesPerPage;
  const currentNotes = notes.slice(indexOfFirstNote, indexOfLastNote);

  useEffect(() => {
    dispatch(getNotesByUserId(user_id));
  }, []);

  const handleNote = (note) => {
    history.push({
      pathname: "/noteEdit",
      state: note,
    });
  };

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <>
      <Wrapper id="notes">
        <HeaderInfo>
          <h1 className="font30 extraBold">Notes</h1>
        </HeaderInfo>
        <div className="whiteBg">
          <div className="">
            <HeaderInfo>
              <Popup
                modal
                trigger={
                  <div style={{ width: "100px" }}>
                    <FullButton title={"Add Note"} />
                  </div>
                }
              >
                {(close) => <NoteModal close={close} />}
              </Popup>
            </HeaderInfo>


            {notes.length === 0 ?
              <EmptyMessage>No notes found. Please try uploading some notes to get started.</EmptyMessage> :
              <div className="row textCenter">
                {currentNotes.map((note, index) => {
                  return (
                    <div
                      key={index}
                      className="col-xs-12 col-sm-4 col-md-4 col-lg-4"
                    >
                      <NoteBox
                        title={note.title}
                        text={`Created Date: ${note.createdDate}`}
                        action={() => handleNote(note)}
                      />
                    </div>
                  );
                })}
              </div>
            }
            <div className="pagination-wrapper">
              {notes.length > notesPerPage && (
                <Pagination
                  notesPerPage={notesPerPage}
                  totalNotes={notes.length}
                  paginate={paginate}
                  currentPage={currentPage}
                />
              )}
            </div>
          </div>
        </div>
      </Wrapper>
    </>
  );
}

function Pagination({ notesPerPage, totalNotes, paginate, currentPage }) {
  const pageNumbers = [];

  for (let i = 1; i <= Math.ceil(totalNotes / notesPerPage); i++) {
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


const Wrapper = styled.section`
  width: 100%;
`;
const HeaderInfo = styled.div`
margin-bottom: 30px;
margin-left: 5px;
@media (max-width: 860px) {
  text-align: center;
}
`
const EmptyMessage = styled.div`
  text-align: center;
  margin: 50px auto;
  font-size: 24px;
  font-weight: bold;
  color: #888;
`;