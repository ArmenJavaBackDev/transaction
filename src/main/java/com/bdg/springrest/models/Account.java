package com.bdg.springrest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;
    private LocalDate createDate;

    @Enumerated(value = EnumType.STRING)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
