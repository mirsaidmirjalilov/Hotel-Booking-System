package com.example.hotelbookingsystem.entity.base;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class AudiTableLong extends AudiTable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
