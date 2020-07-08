package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Cart;
import com.bookworm.demo.model.CartId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    List<Cart> getCarts();

    void saveCart(Cart cart);

    Cart getCart(CartId cartId) throws ResourceNotFoundException;

    void deleteCart(CartId cartId) throws ResourceNotFoundException;

}
