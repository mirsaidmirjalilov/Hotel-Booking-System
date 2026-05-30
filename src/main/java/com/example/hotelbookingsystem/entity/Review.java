package com.example.hotelbookingsystem.entity;

import com.example.hotelbookingsystem.entity.base.AudiTableLong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends AudiTableLong {
    @Column(name = "rating",nullable = false)
    private Integer rating;

    @Column(name = "comment",nullable = false)
    private String comment;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
}
