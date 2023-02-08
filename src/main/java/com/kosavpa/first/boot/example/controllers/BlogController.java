package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import com.kosavpa.first.boot.example.dao.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/blog")
public class BlogController {
    private ArticleRepository articleRepository;

    @Autowired
    public void setPostRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String blog(Model model){
        List<ArticleEntity> posts = articleRepository.findAll();
        model.addAttribute("posts", posts);

        return "blog";
    }
}
