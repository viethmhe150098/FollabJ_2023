import React from "react";
import styled from "styled-components";

export default function FullButton({ title, action, border }) {
  return (
    <Wrapper
      className="animate pointer"
      onClick={action ? () => action() : null}
      border={border}
    >
      {title}
    </Wrapper>
  );
}

const Wrapper = styled.button`
border-radius: 4px;
  border: 1px solid ${(props) => (props.border ? "#fd9309" : "#fd9309")};
  background-color: ${(props) => (props.border ? "transparent" : "#fd9309")};
  width: 100%;
  font-size: 0.938rem;
  font-weight: 600!important;
  padding: 15px;
  outline: none;
  color: ${(props) => (props.border ? "#707070" : "#fff")};
  :hover {
    background-color: ${(props) => (props.border ? "transparent" : "#EB6D09")};
    color: ${(props) => (props.border ? "#fd9309" : "#fff")};
    border: 1px solid #EB6D09;
  }
`;

