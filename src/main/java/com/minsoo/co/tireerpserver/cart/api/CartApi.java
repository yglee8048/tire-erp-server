package com.minsoo.co.tireerpserver.cart.api;

import com.minsoo.co.tireerpserver.cart.model.CartRequest;
import com.minsoo.co.tireerpserver.cart.model.CartResponse;
import com.minsoo.co.tireerpserver.cart.service.CartService;
import com.minsoo.co.tireerpserver.shared.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class CartApi {

    private final CartService cartService;

    @GetMapping(value = "/clients/{clientId}/carts")
    public ApiResponse<List<CartResponse>> findAllCarts(@PathVariable Long clientId) {
        return ApiResponse.OK(cartService.findAllByClientId(clientId)
                .stream()
                .map(CartResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/clients/{clientId}/carts/{cartId}")
    public ApiResponse<CartResponse> findCartById(@PathVariable Long clientId, @PathVariable Long cartId) {
        return ApiResponse.OK(new CartResponse(cartService.findById(cartId)));
    }

    @PostMapping(value = "/clients/{clientId}/carts")
    public ResponseEntity<ApiResponse<Void>> createCart(@PathVariable Long clientId,
                                                        @RequestBody @Valid CartRequest cartRequest) {
        return ApiResponse.CREATED(
                linkTo(methodOn(CartApi.class).findCartById(clientId, cartService.create(cartRequest).getId())).toUri());
    }

    @PutMapping(value = "/clients/{clientId}/carts/{cartId}")
    public ApiResponse<Void> updateCart(@PathVariable Long clientId,
                                        @PathVariable Long cartId,
                                        @RequestBody @Valid CartRequest cartRequest) {
        cartService.update(cartId, cartRequest);
        return ApiResponse.NO_CONTENT;
    }

    @DeleteMapping(value = "/clients/{clientId}/carts/{cartId}")
    public ApiResponse<Void> deleteCart(@PathVariable Long clientId,
                                        @PathVariable Long cartId) {
        cartService.removeById(cartId);
        return ApiResponse.NO_CONTENT;
    }
}
