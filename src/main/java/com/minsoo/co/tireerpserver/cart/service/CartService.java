package com.minsoo.co.tireerpserver.cart.service;

import com.minsoo.co.tireerpserver.cart.entity.Cart;
import com.minsoo.co.tireerpserver.cart.model.CartRequest;
import com.minsoo.co.tireerpserver.cart.repository.CartRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.tire.repository.TireDotRepository;
import com.minsoo.co.tireerpserver.user.entity.Client;
import com.minsoo.co.tireerpserver.user.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ClientRepository clientRepository;
    private final TireDotRepository tireDotRepository;

    public List<Cart> findAllByClientId(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> NotFoundException.of("고객"));
        return cartRepository.findAllByClient(client);
    }

    public Cart findById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> NotFoundException.of("장바구니"));
    }

    public Cart create(CartRequest cartRequest) {
        Client client = clientRepository.findById(cartRequest.getClientId())
                .orElseThrow(() -> NotFoundException.of("고객"));
        TireDot tireDot = tireDotRepository.findById(cartRequest.getTireDotId())
                .orElseThrow(() -> NotFoundException.of("타이어 DOT"));

        return cartRepository.save(Cart.of(client, tireDot, cartRequest.getQuantity()));
    }

    public Cart update(Long cartId, CartRequest cartRequest) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> NotFoundException.of("장바구니"));
        Client client = clientRepository.findById(cartRequest.getClientId())
                .orElseThrow(() -> NotFoundException.of("고객"));
        TireDot tireDot = tireDotRepository.findById(cartRequest.getTireDotId())
                .orElseThrow(() -> NotFoundException.of("타이어 DOT"));

        return cart.update(client, tireDot, cartRequest.getQuantity());
    }

    public void removeById(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> NotFoundException.of("장바구니"));
        cartRepository.delete(cart);
    }
}
