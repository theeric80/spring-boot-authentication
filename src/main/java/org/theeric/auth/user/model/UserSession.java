package org.theeric.auth.user.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.Table;
import org.theeric.auth.core.model.IdentityIdEntity;

@NamedEntityGraphs({ //
        @NamedEntityGraph(name = "session.detail", attributeNodes = { //
                @NamedAttributeNode("user") //
        }) //
})
@Entity
@Table(name = "user_session")
public class UserSession extends IdentityIdEntity {

    private String token;

    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
