package com.example.hotelbookingsystem.entity;

import com.example.hotelbookingsystem.entity.base.AudiTableLong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
