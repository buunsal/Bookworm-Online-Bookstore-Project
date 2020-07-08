package com.bookworm.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@SuppressWarnings("serial")
@Embeddable
public class CartId implements Serializable {

    private String bookId;
    private int shoppingbasketId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CartId that = (CartId) o;
        return Objects.equals(bookId, that.bookId) && Objects.equals(shoppingbasketId, that.shoppingbasketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, shoppingbasketId);
    }

}
