package com.bookworm.demo.controller;

import com.bookworm.demo.model.ShoppingBasket;
import com.bookworm.demo.service.ShoppingBasketService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@Data
@ViewScoped
@Component(value = "customerOrdersController")
public class CustomerOrdersController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<ShoppingBasket> shoppingBasketList;

    @EJB
    private ShoppingBasketService shoppingBasketService;

    @EJB
    private SessionController sessionController;

    @PostConstruct
    public void init() {
        this.shoppingBasketList = shoppingBasketService.getCompletedCustomerShoppingBasket(sessionController.getUser());
        shoppingBasketList.sort(Comparator.comparing(ShoppingBasket::getId).reversed());
    }

}
