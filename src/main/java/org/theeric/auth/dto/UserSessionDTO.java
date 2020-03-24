package org.theeric.auth.dto;

import java.util.Date;
import org.theeric.auth.user.model.UserSession;

public class UserSessionDTO {

    final private Long id;

    final private String token;

    final private Date createdAt;

    public UserSessionDTO(UserSession session) {
        this.id = session.getId();
        this.token = session.getToken();
        this.createdAt = session.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

}
