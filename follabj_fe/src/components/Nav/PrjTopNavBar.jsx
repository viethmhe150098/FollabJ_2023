import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Link as RouterLink } from "react-router-dom";
import Popup from "reactjs-popup";
import Content from "../Modals/CreateProject";

// Components
import Sidebar from "./MobileSideBar";
import Backdrop from "../Elements/Backdrop";
import FullButton from "../Buttons/FullButton";
// Assets
import LogoIcon from "../../assets/svg/Logo";
import BurgerIcon from "../../assets/svg/BurgerIcon";
import { useSelector } from "react-redux";
import axios from "axios";

export default function PrjTopNavBar() {
  const [y, setY] = useState(window.scrollY);
  const [sidebarOpen, toggleSidebar] = useState(false);

  useEffect(() => {
    window.addEventListener("scroll", () => setY(window.scrollY));
    return () => {
      window.removeEventListener("scroll", () => setY(window.scrollY));
    };
  }, [y]);

  const roles = localStorage.getItem("role_name")

  const user_id = useSelector((state) => state.auth.login.currentUser.id)

  const handleSendRequest = () => {
    const leaderRequest = {
      u_id: user_id,
      u_fullname : "user_fullname",
      u_id_number : "placeholder",
    }

    axios.post("http://localhost:8080/user/request", leaderRequest, {
      headers : {
          'Authorization' : "Bearer "+ localStorage.getItem("access_token")
      }
    })
  }

  return (
    <>
      <Sidebar sidebarOpen={sidebarOpen} toggleSidebar={toggleSidebar} />
      {sidebarOpen && <Backdrop toggleSidebar={toggleSidebar} />}
      <Wrapper className="flexCenter animate whiteBg" style={y > 100 ? { height: "60px" } : { height: "80px" }}>
        <NavInner className="container flexSpaceCenter">
          <RouterLink className="pointer flexNullCenter" to="/">
            <LogoIcon />
            <h1 style={{ marginLeft: "15px", color: "black" }} className="font20 extraBold">
              FollabiJ
            </h1>
          </RouterLink>
          <BurderWrapper className="pointer" onClick={() => toggleSidebar(!sidebarOpen)}>
            <BurgerIcon />
          </BurderWrapper>
          <UlWrapper className="flexNullCenter">
          </UlWrapper>
          { roles.includes("LEADER") &&
          <UlWrapperRight className="flexNullCenter">

            <Popup modal trigger={<li className="semiBold pointer font15 flexCenter">
                <FullButton title="Create Project" />
            </li>}>
              {close => <Content close={close} />}
            </Popup>
          </UlWrapperRight>
          }
          {
            !roles.includes("LEADER") &&
            <UlWrapperRight className="flexNullCenter">
                <FullButton title="Send Leader Request" action={()=>{handleSendRequest()}}/>
            </UlWrapperRight>
          }
        </NavInner>
      </Wrapper>
    </>
  );
}

const Wrapper = styled.nav`
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 999;
`;
const NavInner = styled.div`
  position: relative;
  height: 100%;
`
const BurderWrapper = styled.button`
  outline: none;
  border: 0px;
  background-color: transparent;
  height: 100%;
  padding: 0 15px;
  display: none;
  @media (max-width: 760px) {
    display: block;
  }
`;
const UlWrapper = styled.ul`
  display: flex;
  @media (max-width: 760px) {
    display: none;
  }
`;
const UlWrapperRight = styled.ul`
  @media (max-width: 760px) {
    display: none;
  }
`;


