package com.bookworm.demo.controller;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Cart;
import com.bookworm.demo.model.ShoppingBasket;
import com.bookworm.demo.service.ShoppingBasketService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ViewScoped
@Component(value = "orderPageController")
public class OrderPageController implements Serializable {

    private static final long serialVersionUID = 4L;

    private ShoppingBasket shoppingBasket;

    private List<Cart> cartList;

    private Float basketCost = 0.00f;

    @EJB
    private ShoppingBasketService shoppingBasketService;

    @PostConstruct
    public void init() throws ResourceNotFoundException {
        HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        shoppingBasket = shoppingBasketService.getShoppingBasket(Integer.parseInt(httpRequest.getParameter("id")));
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
        }
    }
}
