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
@Table(name = "publisher")
public class Publisher implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "publisherNative"
    )
    @GenericGenerator(
            name = "publisherNative",
            strategy = "native"
    )
    @Column(name = "publisher_id")
    private Integer id;

    @NotEmpty
    private String name;

    private String phoneNumber;

    private String url;

    private String address;
}
