import React from "react";
import CalendarView from "../../components/Elements/CalendarView";
import FullButton from "../../components/Buttons/FullButton";
//import "../style/global.scss"
import AddEvent from "../../components/Modals/AddEvent";
// import UpdateEvent from "../../components/Elements/UpdateEvent";
import Popup from "reactjs-popup";

export default function Calendar() {


  return (
    <>
      {/* <Router> */}
      {/* <nav className="navbar navbar-light bg-light">
     
     <div className="container-fluid align-items-center">
       <Link className="navbar-brand ms-2" to="/">
         <h3>Agenda</h3>
       </Link>
       <span className="navbar-brand mb-0 h2 "><Link className="nav-link pe-0 " to={"/events/add"}>Add Event</Link></span>
     </div>

   </nav> */}

      <div className="">
        {/* <span className=""><Link className="" to={"/events/add"}>Add Event</Link></span> */}



        <Popup modal trigger={<div style={{
          width: "100px", marginLeft:"50px"
        }}>
          <FullButton title={"Add Event"} /></div>}>
          {close => <AddEvent close={close} />
          }
        </Popup>

      </div>
      <CalendarView />
      {/* <Switch>
      <Route path="/events" exact element={<CalendarView/>} />
      <Route path="/events/add" element={<AddEvents/>}/>
      <Route path="/event/:id/update" element={<UpdateEvent/>}/>
    </Switch>
    </Router> */}
    </>
  );
}
