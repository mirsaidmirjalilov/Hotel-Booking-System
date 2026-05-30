package com.example.hotelbookingsystem.entity;

import com.example.hotelbookingsystem.entity.base.AudiTableLong;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "room_inventories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomInventory extends AudiTableLong {
    @Column(name = "date",nullable = false)
    private LocalDate date;

    @Column(name = "available_rooms",nullable = false)
    private Integer availableRooms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
