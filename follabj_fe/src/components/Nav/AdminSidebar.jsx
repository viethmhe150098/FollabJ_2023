import React from 'react';
import {
    FaTh,
    FaUserAlt,
    FaSignal
} from "react-icons/fa";
import { NavLink } from 'react-router-dom';
import { Link as RouterLink } from "react-router-dom";

// Assets
import LogoIcon from "../../assets/svg/Logo";

const AdminSidebar = ({ children }) => {
    const menuItem = [
        {
            path: "/admin/dashboard",
            name: "Statistics",
            icon: <FaSignal />
        },
        {
            path: "/admin/users",
            name: "User Management",
            icon: <FaUserAlt />
        },
        {
            path: "/admin/requests",
            name: "Leader Request Management",
            icon: <FaTh />
        }
      
    ]
    return (
        <>     
        <div className="sidebarContainer">
            <div style={{ width: "300px"}} className="sidebar_sidebar">
                <div className="sidebar_top_section">
                    <NavLink className="pointer flexNullCenter" to='/'>
                        <LogoIcon />
                        <h1 style={{ marginLeft: "15px", color: "black",   fontFamily: "'Khula', sans-serif"}} className="font20 extraBold">
                            FollabiJ
                        </h1>
                    </NavLink>
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

export default AdminSidebar;