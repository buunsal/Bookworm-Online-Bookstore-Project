package com.bookworm.demo.repository;

import com.bookworm.demo.model.Author;
import com.bookworm.demo.model.Book;
import com.bookworm.demo.model.Category;
import com.bookworm.demo.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findBooksByBestsellerTrue();

    List<Book> findBooksByEditorspicksTrue();

    List<Book> findBooksByAuthors(Author author);

    List<Book> findBooksByPublishers(Publisher publisher);

    List<Book> findBooksByCategories(Category category);

    Book findBookByISBN(String isbn);

}
