import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import { getInvitationsByProjectId } from "../../../Redux/invitation/invitationActions";
import { inviteMember } from "../../../Redux/project/projectActions";
import "./inviteUser.css";

const InviteUser = () => {
  const [email, setEmail] = useState("");
  // const [invitedEmails, setInvitedEmails] = useState([]);
  // const [feedbackMessage, setFeedbackMessage] = useState("");
  // const [feedbackMessageClass, setFeedbackMessageClass] = useState("");

  const project_id = useSelector((state) => state.project.currentProject.id);

  const project_invitations = useSelector((state)=> state.invitation.project_invitations)

  const dispatch = useDispatch();

  useEffect(()=> {
    dispatch(getInvitationsByProjectId(project_id))
  },[])

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };

  const handleInvite = () => {
    if (!isValidEmail(email)) {
      // setFeedbackMessage("Please enter a valid email address.");
      // setFeedbackMessageClass("invalid");
      toast.error("Please enter a valid email address.")
      return;
    }


    project_invitations.map((invitation,index) =>{
      if (invitation.receiver.email == email) {
        // setFeedbackMessage("This email has already been invited.");
        // setFeedbackMessageClass("already-invited");
        toast.info("This email has already been invited.")
        return;
      }
    })

    const userdto = {
      email
    }

    dispatch(inviteMember({project_id, userdto})).unwrap().then((result) => {
      const message = result.message
      toast.success(message)
      dispatch(getInvitationsByProjectId(project_id))
    })

    //setInvitedEmails([...invitedEmails, email]);
    // setEmail("");
    // setFeedbackMessage("Invitation sent successfully.");
    // setFeedbackMessageClass("success");
  };

  const isValidEmail = (email) => {
    // A simple email validation regular expression
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  // useEffect(() => {
  //   const timeoutId = setTimeout(() => {
  //     setFeedbackMessage("");
  //     setFeedbackMessageClass("");
  //   }, 2500);

  //   return () => {
  //     clearTimeout(timeoutId);
  //   };
  // }, [feedbackMessage]);

  return (
    <div className="invite-user-container">
      <div className="input-container">
        <input
          type="text"
          placeholder="Enter email address"
          value={email}
          onChange={handleInputChange}
        />
        <button className="invite-button semiBold" onClick={handleInvite}>
          Invite
        </button>
        {/* <div>{feedbackMessage && (
          <div className={`feedback-message ${feedbackMessageClass}`}>
            {feedbackMessage}
          </div>
        )}</div> */}

      </div>


      <div className="invited-emails-container">
        <h3>Invited users:</h3>
        <ul>
          {/* {invitedEmails.map((invitedEmail, index) => (
            <li key={index}>{invitedEmail}</li>
          ))} */}
          {
            project_invitations.map((invitation, index) => 
              <li key={index}>{invitation.receiver.email}</li>
            )
          }
        </ul>
      </div>
    </div>
  );
};

export default InviteUser;
