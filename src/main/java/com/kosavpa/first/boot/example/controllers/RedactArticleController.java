package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.Utils.MultipartFileHelper;
import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import com.kosavpa.first.boot.example.dao.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;


@Controller
@RequestMapping("/blog/{id}/redact")
public class RedactArticleController {
    private ArticleRepository articleRepository;
    private MultipartFileHelper fileHelper;

    @Autowired
    public void setPostRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Autowired
    public void setFileHelper(MultipartFileHelper helper) {
        this.fileHelper = helper;
    }

    @GetMapping
    public String redact(
            @PathVariable(value = "id") long id,
            Model model
    ) {
        if (articleRepository.existsById(id)) {
            articleRepository.findById(id).ifPresent(article -> {
                model.addAttribute("article", article);});

            return "redact";
        }

        return "blog";
    }

    @PostMapping
    public String update(
            @RequestParam MultipartFile uploadFile,
            @ModelAttribute ArticleEntity article
    ) {
        article.setPublicationDate(Calendar.getInstance().getTime());

        if(!uploadFile.isEmpty()) {
            try {
                String fileType = fileHelper.getFileType(uploadFile.getOriginalFilename());

                if (fileHelper.imgFileTypeMatcher(fileType)) {
                    article.setImage(fileHelper.convertToBase64(fileType, uploadFile.getBytes()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        articleRepository.save(article);

        return "redirect:/blog/" + article.getId();
    }
}