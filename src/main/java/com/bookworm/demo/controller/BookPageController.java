package com.bookworm.demo.controller;

import com.bookworm.demo.model.Book;
import com.bookworm.demo.service.BookServiceImpl;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.DecimalFormat;

@Data
@ViewScoped
@Component(value = "bookPageController")
public class BookPageController implements Serializable {

    private static final long serialVersionUID = 4L;

    private static DecimalFormat df = new DecimalFormat("0.00");

    private float discount;
    private String dcPrice;
    private String saveMoneyPrint;

    private float savedMoney;
    private Book book;

    @EJB
    private BookServiceImpl bookService;

    @PostConstruct
    public void init() {
        HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        book = bookService.getBook(httpRequest.getParameter("isbn"));
        if (book.getCampaigns().size() != 0) {
            discount = 0.0f;
            discount = calculate();
            dcPrice = df.format(discount);
            savedMoney = book.getPrice() - discount;
            saveMoneyPrint = df.format(savedMoney);
        }

    }

    public float calculate() {
        int dc = 100 - book.getCampaigns().iterator().next().getDiscount_amount();

        return book.getPrice() * dc / 100;
    }

}
