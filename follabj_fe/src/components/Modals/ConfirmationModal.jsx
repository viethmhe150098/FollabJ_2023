import React from "react";
import styled from "styled-components";

const Modal = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ModalContent = styled.div`
  width: 300px;
  height: 150px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Message = styled.p`
  font-size: 16px;
  margin-bottom: 20px;
  text-align: center;
`;

const Buttons = styled.div`
  display: flex;
  justify-content: space-around;
  width: 100%;
`;

const ConfirmButton = styled.button`
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  background-color: #4caf50;
  color: #fff;
  width: 80px;
  &:hover {
    opacity: 0.8;
  }
`;

const CancelButton = styled.button`
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  background-color: #f44336;
  color: #fff;
width: 80px;
  &:hover {
    opacity: 0.8;
  }
`;

function ConfirmationModal({ message, onConfirm, onCancel }) {
  return (
    <Modal>
      <ModalContent>
        <Message>{message}</Message>
        <Buttons>
          <ConfirmButton onClick={onConfirm}>Yes</ConfirmButton>
          <CancelButton onClick={onCancel}>Cancel</CancelButton>
        </Buttons>
      </ModalContent>
    </Modal>
  );
}

export default ConfirmationModal;
