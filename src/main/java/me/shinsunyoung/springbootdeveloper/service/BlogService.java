package me.shinsunyoung.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.domain.Article;
import me.shinsunyoung.springbootdeveloper.dto.AddArticleRequest;
import me.shinsunyoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.shinsunyoung.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article addArticle(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> getAllArticles() {
        return blogRepository.findAll();
    }

    public Article getArticle(long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found"+ id));
    }

    public void deleteArticle(long id) {
        blogRepository.deleteById(id);
    }

    public Article updateArticle(long id, AddArticleRequest request) {
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found" + id));
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        return blogRepository.save(article);
    }

    @Transactional
    public Article updateArticle(long id, UpdateArticleRequest request) {

        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found" + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }

}
