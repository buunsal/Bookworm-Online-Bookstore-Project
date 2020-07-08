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
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "roleNative"
    )
    @GenericGenerator(
            name = "roleNative",
            strategy = "native"
    )
    @Column(name = "role_id")
    private int id;

    @Column(name = "role")
    private String role;
}
