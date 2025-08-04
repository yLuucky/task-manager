package com.lucky.task_manager.user.domain.models;

import com.lucky.task_manager.user.domain.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID4")
    @UuidGenerator
    @Column(name = "id", columnDefinition = "UUID")
    private UUID userId;
    private String name;
    @Email
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 30)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
