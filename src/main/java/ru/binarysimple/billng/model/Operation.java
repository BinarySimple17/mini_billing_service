package ru.binarysimple.billng.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "operation",
        indexes = @Index(name = "idx_account", columnList = "account_id")
)
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private OperationType type;

    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @ToStringExclude
    private Account account;

    @Column(name = "order_id")
    private Long orderId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }
    }
}