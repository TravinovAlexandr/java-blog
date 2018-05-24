package alex.com.blog.elasticsearch;

import alex.com.blog.domaine.Article;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElasticSearchDao extends ElasticsearchRepository<Article, Long> {
}
