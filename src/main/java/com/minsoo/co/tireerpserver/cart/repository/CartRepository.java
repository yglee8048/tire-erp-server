package com.minsoo.co.tireerpserver.cart.repository;

import com.minsoo.co.tireerpserver.cart.entity.Cart;
import com.minsoo.co.tireerpserver.user.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByClient(Client client);
}
