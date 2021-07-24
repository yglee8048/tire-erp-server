package com.minsoo.co.tireerpserver.cart.entity;

import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.user.entity.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_dot", referencedColumnName = "tire_dot")
    private TireDot tireDot;

    @Column(name = "quantity")
    private Long quantity;

    public static Cart of(Client client, TireDot tireDot, Long quantity) {
        Cart cart = new Cart();
        return cart.update(client, tireDot, quantity);
    }

    public Cart update(Client client, TireDot tireDot, Long quantity) {
        this.client = client;
        this.tireDot = tireDot;
        this.quantity = quantity;

        return this;
    }
}
