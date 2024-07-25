package me.shinsunyoung.springbootdeveloper.controller;

import me.shinsunyoung.springbootdeveloper.dto.ArticleViewResponse;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.dto.ArticleListViewResponse;
import me.shinsunyoung.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {


    private final BlogService blogService;

    @GetMapping("/articleList")
    public String articleList(Model model) {

        List<ArticleListViewResponse> articleList =
                blogService.getAllArticles().stream().map(ArticleListViewResponse::new).toList();

        model.addAttribute("articleList", articleList);
        return "articleList";
    }

    @GetMapping("/article/{id}")
    public String article(@PathVariable(name = "id") Long id, Model model) {

        ArticleViewResponse article = new ArticleViewResponse(blogService.getArticle(id));

        model.addAttribute("article", article);
        return "articleView";
    }

}
