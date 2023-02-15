package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import com.kosavpa.first.boot.example.dao.service.Interface.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class GeneralPageController {

    private ArticleService articleService;
    @Autowired
    public void setPostService(ArticleService service){
        this.articleService = service;
    }

    @GetMapping
    public String mainPackage(Model model){
        Page<ArticleEntity> posts = articleService.pageableFindRequest(0, 3, "id");
        model.addAttribute("threePostForGeneral", posts.getContent());

        return "general";
    }
}
