import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import { getInvitationsByProjectId } from "../../../Redux/invitation/invitationActions";
import { inviteMember } from "../../../Redux/project/projectActions";

const InviteUser = () => {
  const [email, setEmail] = useState("");
  // const [invitedEmails, setInvitedEmails] = useState([]);
  // const [feedbackMessage, setFeedbackMessage] = useState("");
  // const [feedbackMessageClass, setFeedbackMessageClass] = useState("");

  const project_id = useSelector((state) => state.project.currentProject.id);

  const project_invitations = useSelector((state) => state.invitation.project_invitations)

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(getInvitationsByProjectId(project_id))
  }, [])

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };

  const handleInvite = () => {
    if (!isValidEmail(email)) {
      toast.error("Please enter a valid email address.")
      return;
    }
    const userdto = {
      email
    }

    dispatch(inviteMember({ project_id, userdto })).unwrap().then((result) => {
      const message = result.message
      console.log(result.message)
      switch (message) {
        case 'already in project':
          toast.warn('Can not invite yourself or members already in your project!')
          break;
        case 'already invited':
          toast.warn('This email has already been invited!')
          break;
        case 'success':
          toast.success('Invitation sent. Wait for user acceptance!')
          break;
        case 'Not found user':
          toast.warn('Can not found user with this email!')
          break;
        default:
          break;
      }

      dispatch(getInvitationsByProjectId(project_id))
      setEmail("");
    })
  };

  const isValidEmail = (email) => {
    const emailRegex = /^\s*([a-zA-Z0-9_.-]{1,60})@([a-zA-Z0-9_.-]+\.[a-zA-Z0-9_.-]+)\s*$/;
    return emailRegex.test(email);
  };



  return (
    <div className="invite-user-container">
      <div className="input-container">
        <h3>Invite users:</h3>
        <input
          type="text"
          placeholder="Enter email address"
          value={email}
          onChange={handleInputChange}
        />
        <button className="invite-button semiBold" onClick={handleInvite}>
          Invite
        </button>
      </div>


      <div className="invited-emails-container">

        <ul>
          {/* {invitedEmails.map((invitedEmail, index) => (
            <li key={index}>{invitedEmail}</li>
          ))} */}

          {/* {
            project_invitations.map((invitation, index) =>
              <li key={index}>{invitation.receiver.email}</li>
            )
          } */}
        </ul>
      </div>
    </div>
  );
};

export default InviteUser;
