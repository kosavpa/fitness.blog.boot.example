package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;


@Controller
@RequestMapping("/blog/{id}")
public class GetArticleController {
    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public String getArticle(
            @PathVariable(value = "id") Long id,
            Model model
    ){
        if(!postRepository.existsById(id)){

            return "redirect:/blog";
        }

        postRepository.findById(id)
                .ifPresent(article -> {
                    article.setFormattedDate("dd-MM-yyyy");
                    model.addAttribute("article", article);
                });

        return "article";
    }
}
