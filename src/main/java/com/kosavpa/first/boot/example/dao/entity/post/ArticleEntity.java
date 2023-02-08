package com.kosavpa.first.boot.example.dao.entity.post;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blog_article")
public class ArticleEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;
    @Column(name = "anons")
    private String anons;
    @Column(name = "full_text", columnDefinition = "varchar(1000)")
    private String fullText;
    @Transient
    private String formattedDate;

    public void setFormattedPublicationDate(String format){
        formattedDate = new SimpleDateFormat(format).format(publicationDate);
    }
}