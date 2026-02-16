package edu.usf.cs.hogwart_artifact_online.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

@Entity
//not empty from spring boot starter validation

public class Users implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name required")
    private String userName;

    @NotEmpty(message = "password required")
    private String password;

    private boolean enabled;

    @NotEmpty(message = "roles required")
    private String roles; // space separated string
    public Users() {}

    public Users(String userName, Integer id, String password, boolean enabled, String roles) {
        this.userName = userName;
        this.id = id;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
