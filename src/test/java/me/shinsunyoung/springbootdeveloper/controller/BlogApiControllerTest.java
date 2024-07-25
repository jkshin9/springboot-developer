package me.shinsunyoung.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.shinsunyoung.springbootdeveloper.domain.Article;
import me.shinsunyoung.springbootdeveloper.dto.AddArticleRequest;
import me.shinsunyoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.shinsunyoung.springbootdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected BlogRepository blogRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 아티클 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "제목";
        final String content = "내용";
        final AddArticleRequest addArticleRequest = new AddArticleRequest(title, content);

        final String requestJson = objectMapper.writeValueAsString(addArticleRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson));

        // then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);

    }

    @DisplayName("getArticles: 아티클 리스트를 가져온다.")
    @Test
    public void getArticles() throws Exception {
        // given
        final String url = "/api/articles/all";
        final String title = "제목";
        final String content = "내용";

        blogRepository.save(Article.builder().
                title(title)
                .content(content)
                .build());

        // when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));


        // then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value(title))
            .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("getArticles: 아티클 번호로 아티클 정보를 가져온다.")
    @Test
    public void getArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "제목";
        final String content = "내용";


        Article savedArticle =  blogRepository.save(Article.builder().
                    title(title)
                    .content(content)
                    .build());


        // when
        final ResultActions result = mockMvc.perform(get(url, savedArticle.getId()));


        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));

    }


    @DisplayName("deleteArticle: 아티클 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "제목";
        final String content = "내용";


        Article savedArticle = blogRepository.save(Article.builder().
                title(title)
                .content(content)
                .build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();


        assertThat(articles.size()).isEqualTo(0);
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 아티클 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "제목";
        final String content = "내용";

        final String newTitle = "수정된 제목";
        final String newContent = "수정된 내용";

        Article savedArticle = blogRepository.save(Article.builder().
                title(title)
                .content(content)
                .build());

        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updateArticleRequest)));
        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);


    }
}