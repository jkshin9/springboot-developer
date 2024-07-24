package me.shinsunyoung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;

import me.shinsunyoung.springbootdeveloper.dto.ArticleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import me.shinsunyoung.springbootdeveloper.service.BlogService;
import me.shinsunyoung.springbootdeveloper.dto.AddArticleRequest;
import me.shinsunyoung.springbootdeveloper.domain.Article;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(blogService.addArticle(request));
    }

    @GetMapping("/api/articles/all")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {

        List<ArticleResponse> articles = blogService.getAllArticles()
                .stream()
                .map(ArticleResponse::new)
                .toList();


        return ResponseEntity.status(HttpStatus.OK)
                .body(articles);
    }

}
