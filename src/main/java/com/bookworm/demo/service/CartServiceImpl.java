package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Cart;
import com.bookworm.demo.model.CartId;
import com.bookworm.demo.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @EJB
    private CartRepository cartRepository;

    @Override
    @Transactional
    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    @Override
    @Transactional
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart getCart(CartId cartId) throws ResourceNotFoundException {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException(cartId));
    }

    @Override
    @Transactional
    public void deleteCart(CartId cartId) {
        cartRepository.deleteById(cartId);
    }
}
