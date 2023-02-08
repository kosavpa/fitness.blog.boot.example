package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/blog/{id}/delete")
public class DeleteController {
    private ArticleRepository articleRepository;

    @Autowired
    public void setPostRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public String remove(@PathVariable(value = "id") long id){
        articleRepository.deleteById(id);

        return "redirect:/blog";
    }
}
