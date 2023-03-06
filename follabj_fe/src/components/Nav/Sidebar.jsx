import React from 'react';
import {
    FaTh,
    FaRegCommentAlt,
    FaUserAlt,
    FaRegChartBar,
    FaVideo,
    FaPencilAlt,
    FaFolder
} from "react-icons/fa";
import { NavLink } from 'react-router-dom';
import { Link as RouterLink } from "react-router-dom";


// Assets
import LogoIcon from "../../assets/svg/Logo";

const Sidebar = ({ children }) => {
    const menuItem = [
        {
            path: "/tasks",
            name: "Tasks",
            icon: <FaTh />
        },
        {
            path: "/chat",
            name: "Chat",
            icon: <FaRegCommentAlt />
        },
        {
            path: "/events",
            name: "Event",
            icon: <FaRegChartBar />
        },
        {
            path: "/meetingCreate",
            name: "Meeting",
            icon: <FaVideo />
        },
        {
            path: "/notes",
            name: "Notes",
            icon: <FaPencilAlt />
        },
        {
            path: "/files",
            name: "Files",
            icon: <FaFolder />
        },
        {
            path: "/aboutProject",
            name: "About Project",
            icon: <FaUserAlt />
        }
    ]
    return (
        <>     
        <div className="sidebarContainer">
            <div style={{ width: "300px"}} className="sidebar_sidebar">
                <div className="sidebar_top_section">
                    <a className="pointer flexNullCenter" href='/'>
                        <LogoIcon />
                        <h1 style={{ marginLeft: "15px", color: "black",   fontFamily: "'Khula', sans-serif"}} className="font20 extraBold">
                            FollabiJ
                        </h1>
                    </a>
                </div>
                {
                    menuItem.map((item, index) => (
                        <NavLink to={item.path} key={index} className="sidebar_link" activeClassName='sidebar_active'>
                            <div className="sidebar_icon">{item.icon}</div>
                            <div style={{ display:  "block"}} className="sidebar_link_text">{item.name}</div>
                        </NavLink>
                    ))
                }
            </div>
            <main>{children}</main>
        </div>
        </>
    );
};

export default Sidebar;