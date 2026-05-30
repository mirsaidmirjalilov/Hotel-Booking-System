package com.example.hotelbookingsystem.entity;

import com.example.hotelbookingsystem.entity.base.AudiTableLong;
import com.example.hotelbookingsystem.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AudiTableLong {
    @Column(name = "email",unique = true,nullable = false)
    @Email
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "full_name",nullable = false)
    private String fullName;

    @Column(name = "phone_number",nullable = false,unique = true)
    @Pattern(regexp = "^\\+998(9[01345789])\\d{7}$")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_active",nullable = false)
    @ColumnDefault(value = "true")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY)
    private Booking booking;
}
