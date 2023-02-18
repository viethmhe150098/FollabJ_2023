import React from "react";
import styled from "styled-components";
// Components

import FullButton from "../Buttons/FullButton";

// import AboutImg from "../../assets/img/add/about.png";

export default function About() {
  return (
    <Wrapper id="about">
  
      <div className="lightBg">
        <div className="container">
          <Advertising className="flexSpaceCenter">
            <AddLeft>
              <AddLeftInner>
                <ImgWrapper className="flexCenter">
                  {/* <img className="radius8" src={AboutImg} alt="add" /> */}
                </ImgWrapper>
              </AddLeftInner>
            </AddLeft>
            <AddRight>
              <h2 className="font40 extraBold">A few words about FollabiJ</h2>
              <p className="font12">
                Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed
                diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
              </p>
              <ButtonsRow className="flexNullCenter" style={{ margin: "30px 0" }}>
                <div style={{ width: "190px" }}>
                  <FullButton title="Get Started" action={() => alert("clicked")} />
                </div>
                <div style={{ width: "190px", marginLeft: "15px" }}>
                  <FullButton title="Contact Us" action={() => alert("clicked")} border />
                </div>
              </ButtonsRow>
            </AddRight>
          </Advertising>
        </div>
      </div>
    </Wrapper>
  );
}

const Wrapper = styled.section`
  width: 100%;
`;
const HeaderInfo = styled.div`
margin-bottom: 40px;
  @media (max-width: 860px) {
    text-align: center;
  }
`;
const Advertising = styled.div`
  padding: 100px 0;
  margin: 100px 0;
  position: relative;
  @media (max-width: 1160px) {
    padding: 60px 0 40px 0;
  }
  @media (max-width: 860px) {
    flex-direction: column;
    padding: 0 0 30px 0;
    margin: 80px 0 0px 0;
  }
`;
const ButtonsRow = styled.div`
  @media (max-width: 860px) {
    justify-content: space-between;
  }
`;
const AddLeft = styled.div`
  position: relative;
  width: 50%;
  p {
    max-width: 475px;
  }
  @media (max-width: 860px) {
    width: 80%;
    order: 2;
    text-align: center;
    h2 {
      line-height: 3rem;
      margin: 15px 0;
    }
    p {
      margin: 0 auto;
    }
  }
`;
const AddRight = styled.div`
  width: 50%;
  @media (max-width: 860px) {
    width: 80%;
    order: 2;
  }
`;
const AddLeftInner = styled.div`
  width: 100%;
  position: absolute;
  top: -300px;
  left: 0;
  @media (max-width: 1190px) {
    top: -250px;
  }
  @media (max-width: 920px) {
    top: -200px;
  }
  @media (max-width: 860px) {
    order: 1;
    position: relative;
    top: -60px;
    left: 0;
  }
`;
const ImgWrapper = styled.div`
  width: 100%;
  padding: 0 15%;
  img {
    width: 100%;
    height: auto;
  }
  @media (max-width: 400px) {
    padding: 0;
  }
`;
