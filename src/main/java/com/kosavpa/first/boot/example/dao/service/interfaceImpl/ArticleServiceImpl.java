package com.kosavpa.first.boot.example.dao.service.interfaceImpl;


import com.kosavpa.first.boot.example.dao.entity.post.ArticleEntity;
import com.kosavpa.first.boot.example.dao.repository.PostRepository;
import com.kosavpa.first.boot.example.dao.service.Interface.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private PostRepository repository;

    @Autowired
    public void setRepository(PostRepository repository){
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(Long id) {
        return repository.existsById(id);
    }

    @Override
    public ArticleEntity save(ArticleEntity post) {
        return repository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleEntity> pageableFindRequest() {
        Pageable pageable = PageRequest.of(0, 4, Sort.by("date"));

        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleEntity> findAll() {
        return repository.findAll(Sort.by("date"));
    }
}