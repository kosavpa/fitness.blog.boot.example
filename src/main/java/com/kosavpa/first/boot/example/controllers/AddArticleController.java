package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.Utils.MultipartFileHelper;
import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import com.kosavpa.first.boot.example.dao.repository.ArticleRepository;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;


@Controller
@RequestMapping("/add")
public class AddArticleController {
    private ArticleRepository articleRepository;
    private MultipartFileHelper fileHelper;

    @Autowired
    public void setPostRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    @Autowired
    public void setFileHelper(MultipartFileHelper helper){
        this.fileHelper = helper;
    }

    @GetMapping
    public String add(){
        return "add";
    }

    @PostMapping
    public String blog(
            @RequestParam String title,
            @RequestParam MultipartFile uploadFile,
            @RequestParam String anons,
            @RequestParam String fullText
    ){
        String fileType = fileHelper.getFileType(uploadFile.getOriginalFilename());

        if(fileHelper.imgFileTypeMatcher(fileType)) {
            try {
                articleRepository.save(
                        ArticleEntity.builder()
                                .title(title)
                                .image(fileHelper.convertToBase64(fileType, uploadFile.getBytes()))
                                .publicationDate(Calendar.getInstance().getTime())
                                .anons(anons)
                                .fullText(fullText)
                                .build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/blog";
    }
}
