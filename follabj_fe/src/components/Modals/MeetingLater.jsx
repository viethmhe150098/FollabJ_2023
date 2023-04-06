import React, { useState } from "react";
import { toast } from "react-toastify";
import styled from "styled-components";
import { randomID } from "../../screens/ProjectWorkspace/Meeting/Meeting";
import FullButton from "../Buttons/FullButton";

const MeetingLater = ({ close }) => {
    const [meetingLink, setMeetingLink] = useState('');
    const [isLinkGenerated, setIsLinkGenerated] = useState(false);
    const handleScheduledMeeting = () => {
        const roomID = randomID(7);
        const link = `http://localhost:3000/meeting?roomID=${roomID}`;
        setMeetingLink(link);
        setIsLinkGenerated(true);
    };
    return (
        <>
            <Modal>
                {isLinkGenerated ? (
                    <h3>Meeting link generated!</h3>
                ) : (
                    <h3>Click "Generate Link" to create a meeting link</h3>
                )}
                <div className="form-group">
                    <span>{meetingLink} </span>
                    <Wrapper>
                        {isLinkGenerated && (
                            <>
                            <FullButton title={'Copy Link'}
                                action={() => {
                                    navigator.clipboard.writeText(meetingLink);
                                    toast.success("Copied to clipboard!");
                                }}
                            >
                                Copy Link
                            </FullButton>
                            &nbsp;&nbsp;
                            </>
                        )}
                        <FullButton action={handleScheduledMeeting} title={'Generate Link'} />
                    </Wrapper>
                </div>


            </Modal>
        </>
    );

}
const Wrapper = styled.div`
width:400px;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 20px 0;
`;
const Modal = styled.div`

  background-color: #fff;
  padding: 1rem;
  border-radius: 5px;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
  height: 100%;
  h2 {
    font-size: 1.5rem;
    margin-bottom: 1.5rem;
  }
  form {
    .form-group {
      margin-bottom: 1rem;
      label {
        font-weight: bold;
        margin-bottom: 0.5rem;
      }
      input,
      textarea {
        padding: 0.5rem;
        border-radius: 5px;
        border: solid black 1px;
        margin: 0;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }
      .date-picking {
        display: inline-block;
        witdth: 50 %;
        background-color: orange;
        text-color:white
      }
      }
      textarea {
border: 1px black solid;
        resize: none;
      }
      select {
        border: 1px black solid;

        padding: 0.5rem;
        border-radius: 5px;
        border: none;
        margin-bottom: 0.5rem;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }
      }
      label {
        font-weight: bold;
        margin-bottom: 0.5rem;
      }
      input[type='checkbox'] {
        border: 1px black solid;

        margin-right: 0.5rem;
      }
      label {
      }
      #start-date, #end-date {
        border: 1px black solid;
        width: 40%;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }
      }
    }
    button[type='submit'] {
      background-color: orange;
      color: #fff;
      padding: 0.5rem 1rem;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      &:hover {
        background-color: #ff9900;
      }
    }
  }
  .copy-link {
    background-color: orange;
    color: #fff;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    &:hover {
      background-color: #ff9900;
    }
  }
`;


export default MeetingLater