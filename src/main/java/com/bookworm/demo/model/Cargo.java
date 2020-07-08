package com.bookworm.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"shoppingBaskets"})
@ToString(exclude = {"shoppingBaskets"})
@Entity
@Table(name = "cargo")
public class Cargo implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "cargoNative"
    )
    @GenericGenerator(
            name = "cargoNative",
            strategy = "native"
    )
    @Column(name = "cargo_id")
    private Integer id;

    private String name;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String url;

    @Column(name = "shipping_cost")
    private Float shippingCost;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "cargos")
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<ShoppingBasket> shoppingBaskets;

}
