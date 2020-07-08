package com.bookworm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "categoryNative"
    )
    @GenericGenerator(
            name = "categoryNative",
            strategy = "native"
    )
    @Column(name = "category_id")
    private int id;

    private String name;

    private String description;

    @Builder.Default
    private boolean topCategory = false;

}
