import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Link as RouterLink } from "react-router-dom";
import { Link as ScrollLink } from "react-scroll";
import { isLoggedIn } from "../../Redux/auth/auth";

// Components
import Sidebar from "../Nav/MobileSideBar";
import Backdrop from "../Elements/Backdrop";
import FullButton from "../Buttons/FullButton";
// Assets
import LogoIcon from "../../assets/svg/Logo";
import BurgerIcon from "../../assets/svg/BurgerIcon";

export default function TopNavbar() {
  const [y, setY] = useState(window.scrollY);
  const [sidebarOpen, toggleSidebar] = useState(false);
  useEffect(() => {
    window.addEventListener("scroll", () => setY(window.scrollY));
    return () => {
      window.removeEventListener("scroll", () => setY(window.scrollY));
    };
  }, [y]);
  const renderContent = () => {
    if (isLoggedIn()) {
      const roles = localStorage.getItem("role_name");
      if (roles.includes("ADMIN")) {
        return (
          <>
            <li className="semiBold font15 pointer">
              <RouterLink to="admin/dashboard">
                <FullButton title="Dashboard" border />
              </RouterLink>
            </li>
            <li
              className="semiBold pointer flexCenter"
              style={{ marginLeft: "10px" }}
            >
              <RouterLink to="/profile">
                <FullButton title="Profile" />
              </RouterLink>
            </li>
          </>
        );
      } else {
        return (
          <>
            <li className="semiBold font15 pointer">
              <RouterLink to="projects">
                <FullButton title="My Projects" border />
              </RouterLink>
            </li>
            <li
              className="semiBold pointer flexCenter"
              style={{ marginLeft: "10px" }}
            >
              <RouterLink to="/profile">
                <FullButton title="Profile" />
              </RouterLink>
            </li>
          </>
        );
      }
    } else {
      return (
        <>
          <li className="semiBold font15 pointer">
            <RouterLink to="/signup" style={{ padding: "10px 30px 10px 0" }}>
              Sign Up
            </RouterLink>
          </li>
          <li className="semiBold pointer font15 flexCenter">
            <RouterLink to="/login">
              <FullButton title="Login" />
            </RouterLink>
          </li>
        </>
      );
    }
  };

  return (
    <>
      <Sidebar sidebarOpen={sidebarOpen} toggleSidebar={toggleSidebar} />
      {sidebarOpen && <Backdrop toggleSidebar={toggleSidebar} />}

      <Wrapper
        className="flexCenter animate whiteBg"
        style={y > 100 ? { height: "60px" } : { height: "80px" }}
      >
        <NavInner className="container flexSpaceCenter">
          <RouterLink className="pointer flexNullCenter" to="/">
            <LogoIcon />
            <h1
              style={{ marginLeft: "15px", color: "black" }}
              className="font20 extraBold"
            >
              FollabiJ
            </h1>
          </RouterLink>
          <BurderWrapper
            className="pointer"
            onClick={() => toggleSidebar(!sidebarOpen)}
          >
            <BurgerIcon />
          </BurderWrapper>
          <UlWrapper className="flexNullCenter">
            <li className="semiBold font15 pointer">
              <ScrollLink
                activeClass="active"
                style={{ padding: "10px 15px" }}
                to="home"
                spy={true}
                smooth={true}
                offset={-80}
              >
                Home
              </ScrollLink>
            </li>

            <li className="semiBold font15 pointer">
              <ScrollLink
                activeClass="active"
                style={{ padding: "10px 15px" }}
                to="pricing"
                spy={true}
                smooth={true}
                offset={-80}
              >
                Pricing
              </ScrollLink>
            </li>
            <li className="semiBold font15 pointer">
              <ScrollLink
                activeClass="active"
                style={{ padding: "10px 15px" }}
                to="about"
                spy={true}
                smooth={true}
                offset={-80}
              >
                About
              </ScrollLink>
            </li>
            <li className="semiBold font15 pointer">
              <ScrollLink
                activeClass="active"
                style={{ padding: "10px 15px" }}
                to="contact"
                spy={true}
                smooth={true}
                offset={-80}
              >
                Contact
              </ScrollLink>
            </li>
          </UlWrapper>
          <UlWrapperRight className="flexNullCenter">
            {renderContent()}
          </UlWrapperRight>
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
`;
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
