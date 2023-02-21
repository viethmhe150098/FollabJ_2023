import React from "react";
import styled from "styled-components";



const MeetingCreation = () => {

  //handle instant meeting here
  const handleInstantMeeting = () => {
    window.open("http://localhost:3000/meeting");  //edit URL
  };

  const handleScheduledMeeting = () => {
    // Add logic for scheduling a meeting here
    alert("Create scheduled meeting clicked");
  };

  return (
    <>
      <h1>Meeting Page</h1>
      <Wrapper>
        <Button onClick={handleInstantMeeting}>Create Instant Meeting</Button>
        <Button onClick={handleScheduledMeeting}>
          Create Meeting for Later
        </Button>
      </Wrapper>
    </>
  );
};

//style
const Button = styled.button`
  background-color: #ff8800;
  color: white;
  border: none;
  padding: 10px;
  margin-right: 10px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 4px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);

  &:hover {
    background-color: #ff6e00;
  }
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 20px 0;
`;
export default MeetingCreation;
