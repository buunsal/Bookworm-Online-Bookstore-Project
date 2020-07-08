package com.bookworm.demo.controller;

import com.bookworm.demo.model.*;
import com.bookworm.demo.service.BookServiceImpl;
import com.bookworm.demo.service.CartServiceImpl;
import com.bookworm.demo.service.ShoppingBasketServiceImpl;
import com.bookworm.demo.service.UserServiceImpl;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@SessionScope
@Component(value = "shoppingBasketController")
public class ShoppingBasketController implements Serializable {

    private static final long serialVersionUID = 4L;

    private ShoppingBasket shoppingBasket;

    private List<Payment> payments;

    private List<Cargo> cargos;

    private List<Cart> cartList;

    private Integer basketCount;

    private Float basketCost;

    private Boolean freeShipping;

    @EJB
    private SessionController sessionController;

    @EJB
    private ShoppingBasketServiceImpl shoppingBasketService;

    @EJB
    private BookServiceImpl bookService;

    @EJB
    private CartServiceImpl cartService;

    @EJB
    private UserServiceImpl userService;

    @PostConstruct
    public void init() {
        initCart();
    }

    public void AddToCart(Book book) {
        Cart cart = new Cart();
        cart.setShoppingBasket(shoppingBasket);
        cart.setBook(book);
        cart.setBook_count(1);
        cartService.saveCart(cart);

        initCart();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Add To Cart Successful!", "Added Book:\n\n" + book.getTitle()));
    }

    public void remove(Book book) {
        CartId cartId = new CartId();
        cartId.setBookId(book.getISBN());
        cartId.setShoppingbasketId(shoppingBasket.getId());
        cartService.deleteCart(cartId);
        shoppingBasket = shoppingBasketService.getUsersShoppingBasket(sessionController.getUser());
        initCart();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Remove Successful!", "Removed Book:\n\n" + book.getTitle()));
    }

    public void initCart() {
        freeShipping = false;
        shoppingBasket = new ShoppingBasket();
        basketCount = 0;
        basketCost = 0.00f;

        if (sessionController.getIsLogged() && shoppingBasketService.getUsersShoppingBasket(sessionController.getUser()) != null) {
            shoppingBasket = shoppingBasketService.getUsersShoppingBasket(sessionController.getUser());
            cartList = new ArrayList<>(shoppingBasket.getCarts());
            for (Cart cart : cartList) {
                if(cart.getBook().getCampaigns().size()!=0){
                    float dcPrice = cart.getBook().getPrice() *
                            (100 - cart.getBook().getCampaigns().iterator().next().getDiscount_amount()) /100;

                    basketCost += dcPrice * cart.getBook_count();

                }
                else {
                    basketCost += cart.getBook().getPrice() * cart.getBook_count();
                }

                basketCount++;
            }
        } else if (sessionController.getIsLogged() && shoppingBasketService.getUsersShoppingBasket(sessionController.getUser()) == null) {
            sessionController.getUser().getShoppingBaskets().add(shoppingBasket);
            userService.updateUser(sessionController.getUser());
            shoppingBasket = shoppingBasketService.getUsersShoppingBasket(sessionController.getUser());
            cartList = new ArrayList<>();
        }

        if (basketCost > 50) {
            freeShipping = true;
        }

        else {
            freeShipping = false;
        }

    }

    public void newCount(Cart cart) {
        basketCount = 0;
        basketCost = 0.00f;
        cartService.saveCart(cart);
        for (Cart c : cartList) {
            if(c.getBook().getCampaigns().size()!=0){
                float dcPrice = c.getBook().getPrice() *
                        (100 - c.getBook().getCampaigns().iterator().next().getDiscount_amount()) /100;

                basketCost += dcPrice * c.getBook_count();

            }
            else {
                basketCost += c.getBook().getPrice() * c.getBook_count();
            }
            basketCount++;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Order quantity update Successful!", "New Quantity: " + cart.getBook_count()));

        if (basketCost > 50) {
            freeShipping = true;
        }

        else {
            freeShipping = false;
        }
    }

    public boolean addedButton(Book book) {
        if (cartList != null) {
            for (Cart cart : cartList) {
                if (cart.getBook().equals(book)) return true;
            }
        }

        return false;
    }

}