package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/blog/{id}")
public class GetArticleController {
    private ArticleRepository articleRepository;

    @Autowired
    public void setPostRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String getArticle(
            @PathVariable(value = "id") Long id,
            Model model
    ){
        if(!articleRepository.existsById(id)){

            return "redirect:/blog";
        }

        articleRepository.findById(id)
                .ifPresent(article -> {
                    article.setFormattedDate("dd-MM-yyyy");
                    model.addAttribute("article", article);
                });

        return "more";
    }
}
