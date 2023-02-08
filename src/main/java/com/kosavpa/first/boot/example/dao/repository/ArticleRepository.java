package com.kosavpa.first.boot.example.dao.repository;


import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
}
