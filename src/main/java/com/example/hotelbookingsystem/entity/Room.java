package com.example.hotelbookingsystem.entity;

import com.example.hotelbookingsystem.entity.base.AudiTableLong;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends AudiTableLong {
    @Column(name = "room_type",nullable = false)
    private String roomType;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "capacity",nullable = false)
    @Min(1)
    private Integer capacity;

    @Column(name = "basePrice",nullable = false)
    @Min(1)
    private BigDecimal basePrice;

    @Column(name = "image_urls",nullable = false)
    @ElementCollection
    private List<String> imageUrls;

    @Column(name = "total_rooms",nullable = false)
    private Integer totalRooms;

    @Column(name = "is_active",nullable = false)
    @ColumnDefault(value = "true")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "room")
    private List<RoomInventory> roomInventory;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "room")
    private List<Booking> booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
