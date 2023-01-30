package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/blog/{id}/delete")
public class DeleteController {
    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public String remove(@PathVariable(value = "id") long id){
        postRepository.deleteById(id);

        return "redirect:/blog";
    }
}
