package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import com.kosavpa.first.boot.example.dao.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;


@Controller
@RequestMapping("/blog/{id}/redact")
public class RedactArticleController {
    private ArticleRepository articleRepository;

    @Autowired
    public void setPostRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String redact(
            @PathVariable(value = "id") long id,
            Model model
    ){
        if(articleRepository.existsById(id)){
            articleRepository.findById(id).ifPresent(article -> model.addAttribute("article", article));

            return "redact";
        }

        return "redirect:/blog";
    }

    @PostMapping
    public String update(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String fullText,
            @PathVariable(value = "id") long id,
            Model model
    ){
        articleRepository.save(
                ArticleEntity.builder()
                        .id(id)
                        .title(title)
                        .publicationDate(Calendar.getInstance().getTime())
                        .anons(anons)
                        .fullText(fullText)
                        .build());

        return "/blog/" + id;
    }
}