package com.opencrm.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "contacts")
public class Contact extends NameEntity {
    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String jobTitle;

    @Column
    private String timezone;

    @Column
    private String avatarUrl;

    @Column
    private Integer score;

    @Column
    private Long companyId;
}
