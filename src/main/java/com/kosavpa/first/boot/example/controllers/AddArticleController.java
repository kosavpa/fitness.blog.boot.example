package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import com.kosavpa.first.boot.example.dao.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;


@Controller
@RequestMapping("/add")
public class AddArticleController {
    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public String add(){
        return "add";
    }

    @PostMapping
    public String blog(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String fullText
    ){
        postRepository.save(
                ArticleEntity.builder()
                        .title(title)
                        .publicationDate(Calendar.getInstance().getTime())
                        .anons(anons)
                        .fullText(fullText)
                        .build());

        return "redirect:/blog";
    }
}
