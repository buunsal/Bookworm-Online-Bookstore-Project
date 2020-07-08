package com.bookworm.demo.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"shoppingBasket"})
@ToString(exclude = {"shoppingBasket"})
@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "paymentNative"
    )
    @GenericGenerator(
            name = "paymentNative",
            strategy = "native"
    )
    @Column(name = "payment_id")
    private Integer id;

    private Float amount;

    private Boolean status;

    @CreationTimestamp
    private LocalDateTime date;

    @OneToOne(mappedBy = "payment")
    private ShoppingBasket shoppingBasket;

}
