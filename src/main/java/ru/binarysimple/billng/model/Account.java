package ru.binarysimple.billng.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "account",
        indexes = @Index(name = "idx_username", columnList = "username")
)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "balance", precision = 19, scale = 2)
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    @OrderBy("createdAt")
    private List<Operation> operations = new ArrayList<>();
}