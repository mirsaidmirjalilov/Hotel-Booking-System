package com.example.hotelbookingsystem.entity;

import com.example.hotelbookingsystem.entity.base.AudiTableLong;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel extends AudiTableLong {
    @Column(name = "hotel_name",nullable = false)
    private String hotelName;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "phone_number",nullable = false,unique = true)
    @Pattern(regexp = "^\\+998(9[01345789])\\d{7}$")
    private String phoneNumber;

    @Column(name = "is_active",nullable = false)
    @ColumnDefault(value = "true")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hotel")
    private List<Room> rooms;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hotel")
    private List<Review> reviews;
}
