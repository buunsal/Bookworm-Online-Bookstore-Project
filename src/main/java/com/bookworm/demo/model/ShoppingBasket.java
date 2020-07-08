package com.bookworm.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"carts", "payment", "cargos"})
@ToString(exclude = {"carts", "payment", "cargos"})
@Entity
@Table(name = "shoppingbasket")
public class ShoppingBasket implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "basketNative"
    )
    @GenericGenerator(
            name = "basketNative",
            strategy = "native"
    )
    @Column(name = "shoppingbasket_id")
    private Integer id;

    private boolean finished;

    @OneToMany(mappedBy = "shoppingBasket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Cart> carts;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Payment payment;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "shoppingbasket_cargo", joinColumns = @JoinColumn(name = "shoppingbasket_id"), inverseJoinColumns = @JoinColumn(name = "cargo_id"))
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Cargo> cargos;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "shoppingBaskets")
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<User> users;

}
