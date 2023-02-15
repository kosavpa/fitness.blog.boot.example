package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import com.kosavpa.first.boot.example.dao.repository.ArticleRepository;
import com.kosavpa.first.boot.example.dao.service.Interface.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/blog")
public class BlogController {
    private ArticleService articleService;

    @Autowired
    public void setPostRepository(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String blog(@RequestParam(name = "page", required = false) Integer page, Model model){
        if (page != null) {
            page--;
        } else {
            page = 0;
        }

        Long repoCount = articleService.count();
        int pageCount = (int)Math.ceil(repoCount.doubleValue()/6.0);
        List<Integer> pages = IntStream.range(1, ++pageCount).boxed().toList();

        List<ArticleEntity> posts = articleService.pageableFindRequest(page, 6, "id").getContent();
        model.addAttribute("posts", posts);
        model.addAttribute("pages", pages);

        return "blog";
    }
}
