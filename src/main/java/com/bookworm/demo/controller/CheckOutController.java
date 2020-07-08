package com.bookworm.demo.controller;

import com.bookworm.demo.model.*;
import com.bookworm.demo.service.BookService;
import com.bookworm.demo.service.PaymentService;
import com.bookworm.demo.service.ShoppingBasketService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@SessionScope
@Component(value = "checkOutController")
public class CheckOutController implements Serializable {

    private static final long serialVersionUID = 4L;

    private Cargo selectedCargo;

    private Float totalCost;

    private String number;

    private String name;

    private String expiry;

    private String cvc;

    private Boolean isStockEnough;

    private List<Cart> cartList;

    @EJB
    private ShoppingBasketController shoppingBasketController;

    @EJB
    private PaymentService paymentService;

    @EJB
    private BookService bookService;

    @EJB
    private ShoppingBasketService shoppingBasketService;

    @PostConstruct
    public void init() {
        totalCost = shoppingBasketController.getBasketCost();
    }

    public void selectCargo(Cargo cargo) {

        this.selectedCargo = cargo;

        if (!shoppingBasketController.getFreeShipping()){
            this.totalCost = shoppingBasketController.getBasketCost() + shoppingBasketController.getBasketCount() + selectedCargo.getShippingCost();
        }

        else {
            this.totalCost = shoppingBasketController.getBasketCost();
        }

    }

    public void completeShopping() throws IOException {
        cartList = new ArrayList<>(shoppingBasketController.getShoppingBasket().getCarts());
        StringBuilder stringBuilder = new StringBuilder();

        isStockEnough = true;

        for (Cart cart : cartList) {
            if (cart.getBook().getStock() < cart.getBook_count()) {
                isStockEnough = false;
                stringBuilder
                        .append(cart.getBook().getTitle())
                        .append("'s available stock is ")
                        .append(cart.getBook().getStock())
                        .append(".\n");
            }
        }

        stringBuilder.append("\nPlease check your order!\n");

        if (isStockEnough) {
            for (Cart cart : cartList) {
                cart.getBook().setStock(cart.getBook().getStock() - cart.getBook_count());
                bookService.saveBook(cart.getBook());
            }

            Payment payment = new Payment();
            payment.setAmount(totalCost);
            payment.setStatus(true);
            payment.setShoppingBasket(shoppingBasketController.getShoppingBasket());
            paymentService.savePayment(payment);
            shoppingBasketController.getShoppingBasket().setPayment(payment);
            shoppingBasketController.getShoppingBasket().setFinished(true);
            shoppingBasketController.getShoppingBasket().setCargos(Collections.singleton(selectedCargo));
            shoppingBasketService.saveShoppingBasket(shoppingBasketController.getShoppingBasket());
            shoppingBasketController.initCart();
            this.selectedCargo = null;
            FacesContext.getCurrentInstance().getExternalContext().redirect("/customer/customerpage.xhtml");

        }

        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Out Of Stock!", String.valueOf(stringBuilder)));
        }

    }

    public String bookRating(Book book) {
        float total = 0;

        for (Review r : book.getReviews()) {
            total += r.getRating();
        }

        return String.valueOf(total / book.getReviews().size());
    }

    public String bookRoundedRating(Book book) {
        float total = 0;
        for (Review r : book.getReviews()) {
            total += r.getRating();
        }

        return String.valueOf((int) (total / book.getReviews().size()));
    }
}
