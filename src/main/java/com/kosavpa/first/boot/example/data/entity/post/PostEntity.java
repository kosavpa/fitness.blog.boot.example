package com.kosavpa.first.boot.example.data.entity.post;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "post_entity")
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "publication")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;
    @Column(name = "anons")
    private String anons;
    @Column(columnDefinition = "varchar(1000)")
    private String fullText;
}