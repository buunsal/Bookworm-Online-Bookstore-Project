package com.bookworm.demo.repository;

import com.bookworm.demo.model.ShoppingBasket;
import com.bookworm.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, Integer> {

    ShoppingBasket findShoppingBasketByUsersAndFinishedIsFalse(User user);

    List<ShoppingBasket> findShoppingBasketsByUsersAndFinishedIsTrue(User user);

    List<ShoppingBasket> findShoppingBasketsByFinishedIsTrue();

}
