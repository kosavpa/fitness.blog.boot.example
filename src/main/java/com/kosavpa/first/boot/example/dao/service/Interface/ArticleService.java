package com.kosavpa.first.boot.example.dao.service.Interface;


import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface ArticleService {
    boolean isExist(Long id);
    ArticleEntity save(ArticleEntity post);
    Optional<ArticleEntity> findById(Long id);
    void deleteById(Long id);
    Page<ArticleEntity> pageableFindRequest();
    Iterable<ArticleEntity> findAll();
}
