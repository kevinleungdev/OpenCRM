package com.opencrm.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
public class User extends NameEntity {
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column
    private String jobTitle;

    @Column
    private String timezone;

    @Column
    private String avatarUrl;

    @Column
    private String passwordHash;
}
