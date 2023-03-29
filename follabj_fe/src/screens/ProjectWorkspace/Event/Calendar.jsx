import React from "react";
import CalendarView from "./CalendarView";
import FullButton from "../../../components/Buttons/FullButton";
//import "../style/global.scss"
import AddEvent from "../../../components/Modals/AddEvent";
// import UpdateEvent from "../../components/Elements/UpdateEvent";
import Popup from "reactjs-popup";
import { useSelector } from "react-redux";
import styled from "styled-components";



export default function Calendar() {
  const userRole = useSelector((state) => state.project.currentProject.userRole);
  const HeaderInfo = styled.div`
  margin-bottom: 30px;
  margin-left: 5px;
  @media (max-width: 860px) {
    text-align: center;
  }
`

  return (
    <>
      <HeaderInfo>
        <h1 className="font30 extraBold">Events Calendar</h1>
      </HeaderInfo>
      <div className="">
        {userRole == "LEADER" &&
          <Popup modal trigger={<div style={{
            width: "100px"
          }}>
            <FullButton title={"Add Event"} /></div>}>
            {close => <AddEvent close={close} />
            }
          </Popup>
        }
      </div>
      <CalendarView />
    </>
  );


}
