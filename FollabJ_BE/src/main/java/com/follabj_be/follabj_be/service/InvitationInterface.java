package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Invitation;

import java.util.List;

public interface InvitationInterface  {

    List<Invitation> getInvitationsByProjectId(Long project_id);

    Invitation addInvitation(Invitation invitation);

    Invitation getInvitationByReceiverIdAndProjectId(Long receiver_id, Long project_id);

    void deleteInvitation(Long invitation_id);

    List<Invitation> getInvitationsByUserId(Long user_id);

    void updateStatus(int status, Long i_id);

    boolean checkIfInvitationBelongToUser(Long invitation_id, Long user_id);
}
