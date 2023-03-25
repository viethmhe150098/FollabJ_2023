package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Invitation;

import java.util.List;

public interface InvitationInterface  {

    List<Invitation> getInvitationsByProjectId(Long project_id);

    Invitation addInvitation(Invitation invitation);

    List<Invitation> getInvitationsByUserId(Long user_id);

    void updateStatus(int status, Long i_id);
}
