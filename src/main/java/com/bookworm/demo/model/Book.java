package com.bookworm.demo.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"authors", "categories", "publishers", "reviews", "campaigns", "carts"})
@ToString(exclude = {"authors", "categories", "publishers", "reviews", "campaigns", "carts"})
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @Column(name = "isbn")
    private String ISBN;

    private String title;

    private String summary;

    private String picture;

    @Past(message = "Year must past time!")
    private Date year;

    @Positive(message = "Price value must be positive!")
    private Float price;

    @PositiveOrZero(message = "Stock value must be positive or negative!")
    private int stock;

    @Builder.Default
    private boolean bestseller = false;
    
    @Builder.Default
    private boolean editorspicks = false;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Cart> carts;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "book_publisher", joinColumns = @JoinColumn(name = "isbn"), inverseJoinColumns = @JoinColumn(name = "publisher_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Publisher> publishers;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "isbn"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_category", joinColumns = @JoinColumn(name = "isbn"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_review", joinColumns = @JoinColumn(name = "isbn"), inverseJoinColumns = @JoinColumn(name = "review_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Review> reviews;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_campaign", joinColumns = @JoinColumn(name = "isbn"), inverseJoinColumns = @JoinColumn(name = "campaign_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Campaign> campaigns;

    public String searchString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.title);
        stringBuilder.append(" by ");
        for (Author a : this.authors) {
            stringBuilder.append(a.getName());
            stringBuilder.append(" ");
        }
        stringBuilder.append(" / ");
        stringBuilder.append(this.ISBN);
        return String.valueOf(stringBuilder);
    }

}
