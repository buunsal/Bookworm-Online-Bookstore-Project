package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.ShoppingBasket;
import com.bookworm.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingBasketService {

    List<ShoppingBasket> getShoppingBaskets();

    void saveShoppingBasket(ShoppingBasket shopping);

    ShoppingBasket getShoppingBasket(int id) throws ResourceNotFoundException;

    ShoppingBasket getUsersShoppingBasket(User user);

    List<ShoppingBasket> getCompletedCustomerShoppingBasket(User user);

    List<ShoppingBasket> getShoppingBasketFinished();

    void deleteShoppingBasket(int id) throws ResourceNotFoundException;

}
