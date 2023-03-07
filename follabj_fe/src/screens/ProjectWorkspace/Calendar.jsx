import React from "react";
import CalendarView from "../../components/Sections/CalendarView";
import FullButton from "../../components/Buttons/FullButton";
//import "../style/global.scss"
import AddEvent from "../../components/Modals/AddEvent";
// import UpdateEvent from "../../components/Elements/UpdateEvent";
import Popup from "reactjs-popup";
import { useSelector } from "react-redux";

export default function Calendar() {
  const userRole = useSelector((state) => state.project.currentProject.userRole);

  return (
    <>
      <div className="">
        { userRole == "LEADER" &&
        <Popup modal trigger={<div style={{
          width: "100px", marginLeft:"50px"
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
