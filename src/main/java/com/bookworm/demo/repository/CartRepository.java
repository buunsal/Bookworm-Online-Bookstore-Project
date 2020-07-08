package com.bookworm.demo.repository;

import com.bookworm.demo.model.Cart;
import com.bookworm.demo.model.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {

}
