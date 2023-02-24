import React , { useEffect, useState } from 'react'
import { Calendar, dateFnsLocalizer } from 'react-big-calendar'
import format from 'date-fns/format'
import parse from 'date-fns/parse'
import startOfWeek from 'date-fns/startOfWeek'
import getDay from 'date-fns/getDay'
import enUS from 'date-fns/locale/en-US'
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { useDispatch, useSelector } from 'react-redux'
import { getEventsByProjectId } from '../../Redux/event/eventActions'
import FullButton from '../Buttons/FullButton'
import Popup from 'reactjs-popup'
import CreateEventForm from './AddEvent'

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

    useEffect(()=>{
      dispatch(getEventsByProjectId(1));
    },[])

    const events = useSelector((state) => state.event)

    return (
    <div>
        <Popup modal trigger={<button className='purpleBg font25 radius6 lightColor tag'>Add Event</button>}>
            {close => <CreateEventForm close={close} />}
        </Popup>
        <Calendar
            localizer={localizer}
            events={events}
            startAccessor={(event) => { return new Date(event.start)}}
            endAccessor={(event) => { return new Date(event.end)  }}
            style={{ height: 750 , margin: 50, fontFamily: 'Patrick Hand' }}
            // onSelectEvent={(event)=>{console.log(moment(event.startDate).format("YYYY-MM-DD HH:mm:ss"))}}
        />
    </div>
        
    )
}

export default CalendarView
//export default connect(mapStateToProps, {ShowEventApi, closeEvent, ShowEventsApi})(CalendarView)