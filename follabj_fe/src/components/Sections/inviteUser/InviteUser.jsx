import React, { useState, useEffect } from "react";
import "../inviteUser/inviteUser.css";

const InviteUser = () => {
  const [email, setEmail] = useState("");
  const [invitedEmails, setInvitedEmails] = useState([]);
  const [feedbackMessage, setFeedbackMessage] = useState("");
  const [feedbackMessageClass, setFeedbackMessageClass] = useState("");

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };

  const handleInvite = () => {
    if (!isValidEmail(email)) {
      setFeedbackMessage("Please enter a valid email address.");
      setFeedbackMessageClass("invalid");
      return;
    }
    if (invitedEmails.includes(email)) {
      setFeedbackMessage("This email has already been invited.");
      setFeedbackMessageClass("already-invited");
      return;
    }
    setInvitedEmails([...invitedEmails, email]);
    setEmail("");
    setFeedbackMessage("Invitation sent successfully.");
    setFeedbackMessageClass("success");
  };

  const isValidEmail = (email) => {
    // A simple email validation regular expression
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      setFeedbackMessage("");
      setFeedbackMessageClass("");
    }, 2500);

    return () => {
      clearTimeout(timeoutId);
    };
  }, [feedbackMessage]);

  return (
    <div className="invite-user-container">
      <div className="input-container">
        <input
          type="text"
          placeholder="Enter email address"
          value={email}
          onChange={handleInputChange}
        />
        <button className="invite-button" onClick={handleInvite}>
          Invite
        </button>
      </div>
      {feedbackMessage && (
        <div className={`feedback-message ${feedbackMessageClass}`}>
          {feedbackMessage}
        </div>
      )}

      <div className="invited-emails-container">
        <h3>Invited emails:</h3>
        <ul>
          {invitedEmails.map((invitedEmail, index) => (
            <li key={index}>{invitedEmail}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default InviteUser;
