package alex.com.blog.service;


import alex.com.blog.domaine.Article;
import alex.com.blog.domaine.User;
import alex.com.blog.dto.ArticleValidDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    void addArticle(ArticleValidDto article);
    boolean uidExists(String uid);
    List<Article> getPageableArticles(User user, Pageable pageable);
    List<Article> findArticlesInRange(Long range);
    Article findByUid(String uid);
    List<Article> findAll();
}
