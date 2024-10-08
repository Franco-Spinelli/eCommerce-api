package com.mateocuevas.ecommerceapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mateocuevas.ecommerceapi.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private boolean hasDelivery;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address deliveryAddress;
    @Column(nullable = false)
    @Min(value = 1, message = "totalItems must be greater than 0")
    private Integer totalItems;
    private double totalPrice;
    private Date date;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OrderItem> orderItems = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(nullable = false)
    private boolean archivedByCustomer = false;
    @Column(nullable = false)
    private boolean archivedByAdmin = false;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
