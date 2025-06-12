package com.project.swapskill_backend.DTO;

import com.project.swapskill_backend.Model.UserAuthenticationModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    @OneToOne
    private UserAuthenticationModel user;
    private LocalDateTime expiryDateTime;

    public PasswordResetToken() {
    }

    public PasswordResetToken(long id, String token, UserAuthenticationModel user, LocalDateTime expiryDateTime) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDateTime = expiryDateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserAuthenticationModel getUser() {
        return user;
    }

    public void setUser(UserAuthenticationModel user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDateTime=" + expiryDateTime +
                '}';
    }
}
