package com.bookworm.demo.converter;

import com.bookworm.demo.model.Book;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@Data
@Scope(value = "view")
@Component(value = "bookConverter")
@FacesConverter(value = "bookConverter", managed = true)
public class BookConverter implements Converter<Book> {

    @Override
    public Book getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                Book book = new Book();
                book.setISBN(value);
                return book;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Book book) {
        if (book != null) {
            return String.valueOf(book.getISBN());
        } else {
            return null;
        }
    }
}