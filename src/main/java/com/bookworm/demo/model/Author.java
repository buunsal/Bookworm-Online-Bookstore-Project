package com.bookworm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "authorNative"
    )
    @GenericGenerator(
            name = "authorNative",
            strategy = "native"
    )
    @Column(name = "author_id")
    private Integer id;

    @NotEmpty
    private String name;

    private String email;

    private String url;

    @Builder.Default
    private boolean topAuthor = false;

}
