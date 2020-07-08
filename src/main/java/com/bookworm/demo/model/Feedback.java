package com.bookworm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "feedbackNative"
    )
    @GenericGenerator(
            name = "feedbackNative",
            strategy = "native"
    )
    @Column(name = "feedback_id")
    private Integer id;

    private String email;

    private String content;

    @CreationTimestamp
    private LocalDateTime date;

}
