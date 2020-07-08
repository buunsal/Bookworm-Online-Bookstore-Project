package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.ShoppingBasket;
import com.bookworm.demo.model.User;
import com.bookworm.demo.repository.ShoppingBasketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class ShoppingBasketServiceImpl implements ShoppingBasketService {

    @EJB
    private ShoppingBasketRepository shoppingBasketRepository;

    @Override
    @Transactional
    public List<ShoppingBasket> getShoppingBaskets() {
        return shoppingBasketRepository.findAll();
    }

    @Override
    @Transactional
    public void saveShoppingBasket(ShoppingBasket shoppingBasket) {
        shoppingBasketRepository.save(shoppingBasket);
    }

    @Override
    @Transactional
    public ShoppingBasket getShoppingBasket(int id) throws ResourceNotFoundException {
        return shoppingBasketRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
    }

    @Override
    public ShoppingBasket getUsersShoppingBasket(User user) {
        return shoppingBasketRepository.findShoppingBasketByUsersAndFinishedIsFalse(user);
    }

    @Override
    public List<ShoppingBasket> getShoppingBasketFinished() {
        return shoppingBasketRepository.findShoppingBasketsByFinishedIsTrue();
    }

    @Override
    public List<ShoppingBasket> getCompletedCustomerShoppingBasket(User user) {
        return shoppingBasketRepository.findShoppingBasketsByUsersAndFinishedIsTrue(user);
    }

    @Override
    @Transactional
    public void deleteShoppingBasket(int id) {
        shoppingBasketRepository.deleteById(id);
    }
}
