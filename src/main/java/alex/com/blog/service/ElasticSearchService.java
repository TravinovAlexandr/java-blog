package alex.com.blog.service;

import alex.com.blog.domaine.Article;
import java.util.List;

public interface ElasticSearchService {
     void save(List<Article> list);
     void save(Article article);
     List<Article> findByHeaderStartsWith(String header);
}
