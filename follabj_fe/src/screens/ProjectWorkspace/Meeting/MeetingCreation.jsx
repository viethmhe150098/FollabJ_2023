import React from "react";
import styled from "styled-components";
import FullButton from "../../../components/Buttons/FullButton";
import { randomID } from "./Meeting";
import MeetingLater from "../../../components/Modals/MeetingLater";
import Popup from "reactjs-popup";

const MeetingCreation = () => {

  //handle instant meeting here
  const handleInstantMeeting = () => {
    window.open("http://localhost:3000/meeting");  //edit URL
  };

  const handleScheduledMeeting = () => {
    // Add logic for scheduling a meeting here
    const roomID = randomID(7);
    alert(`http://localhost:3000/meeting?roomID=${roomID}`);

  };


  return (
    <>
      <HeaderInfo>
        <h1 className="font30 extraBold">Meeting</h1>
      </HeaderInfo>
      <Wrapper>
        <FullButton title={'Create Instant Meeting'} action={() => { handleInstantMeeting() }}></FullButton>
        &nbsp;&nbsp;&nbsp;&nbsp;

        <Popup
          modal
          trigger={
            <div style={{width:'100%'}}>
              <FullButton title={' Create Meeting for Later'} />
            </div>
          }
        >
          {(close) => <MeetingLater close={close} />}
        </Popup>
      </Wrapper>
    </>
  );
};

//style

const Wrapper = styled.div`
width:700px;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 20px 0;
`;
const HeaderInfo = styled.div`
margin-bottom: 30px;
margin-left: 5px;
@media (max-width: 860px) {
  text-align: center;
}
`
export default MeetingCreation;
