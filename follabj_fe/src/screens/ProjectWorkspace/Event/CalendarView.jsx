import React , { useEffect } from 'react'
import { Calendar, dateFnsLocalizer } from 'react-big-calendar'
import format from 'date-fns/format'
import parse from 'date-fns/parse'
import startOfWeek from 'date-fns/startOfWeek'
import getDay from 'date-fns/getDay'
import enUS from 'date-fns/locale/en-US'
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { useDispatch, useSelector } from 'react-redux'
import { getEventsByUserId } from '../../../Redux/event/eventActions'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css';
import CreateEventForm from '../../../components/Modals/AddEvent';
import { useState } from 'react'
import Popup from 'reactjs-popup'
const locales = {
  'en-US': enUS,
}

const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek,
  getDay,
  locales,
})

const CalendarView = () => {

    const dispatch = useDispatch();

    const events = useSelector((state) => state.event)

    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const projectId = useSelector((state) => state.project.currentProject.id);

    const [selectedEvent, setSelectedEvent] = useState(null)

    // const history = useHistory();

    if (projectId == null) {
        // history.push("/projects")
        window.location.href = "/projects";

    }

    useEffect(()=>{
      // if (events.length == 0)
      dispatch(getEventsByUserId(user_id));
      // notify();
      // eslint-disable-next-line react-hooks/exhaustive-deps
    },[])

    // const message = useSelector((state) => state.responseMessage)

    // const notify = () => {
    //   if (message.success != "") {
    //       toast.success(message.success, {
    //           position: "top-right",
    //           autoClose: 3000,
    //           hideProgressBar: false,
    //           closeOnClick: true,
    //           progress: undefined,
    //           theme: "light",
    //       });;
    //   }
    //   if (message.error != "") {
    //       toast.error(message.error, {
    //           position: "top-right",
    //           autoClose: 3000,
    //           hideProgressBar: false,
    //           closeOnClick: true,
    //           progress: undefined,
    //           theme: "light",
    //       });;
    //   }
    // } 
    
    const openEventModal = (event) => {
      setSelectedEvent(event);

      // console.log(event);
      // console.log("opening modal")

      const button = document.getElementById("openButton");
      button.click()
    }

    return (
      <>
      <ToastContainer />
      <Popup modal trigger={<button id="openButton"></button>}>
          {close => <CreateEventForm close={close} type={"readonly"} event={selectedEvent}/>}
      </Popup>
    <div>
        <Calendar
            localizer={localizer}
            events={events}
            startAccessor={(event) => { return new Date(event.start)}}
            endAccessor={(event) => { return new Date(event.end)  }}
            style={{ height: 750 , margin: 50, fontFamily: 'Patrick Hand' }}
            onSelectEvent={(event)=>{openEventModal(event)}}
        />
    </div>
    </>
        
    )
}

export default CalendarView
