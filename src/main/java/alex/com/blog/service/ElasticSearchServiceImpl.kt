package alex.com.blog.service

import alex.com.blog.domaine.Article
import alex.com.blog.elasticsearch.ElasticSearchDao
import alex.com.blog.exception.ServiceException
import org.apache.log4j.Logger
import org.elasticsearch.index.query.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.query.SearchQuery
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Service
open class ElasticSearchServiceImpl @Autowired constructor(private var elasticSearchDao: ElasticSearchDao,
                                                           private var elasticsearchTemplate: ElasticsearchOperations) : ElasticSearchService {

    private val LOGGER = Logger.getLogger(ElasticSearchService::class.java.simpleName)

    override fun findByHeaderStartsWith(header: String?): MutableList<Article> {
        if (header === null) {
            LOGGER.debug("Param String is null", NullPointerException())
            throw ServiceException(NullPointerException())
        }
        try {
            val queryBuilder: QueryBuilder = QueryBuilders
                    .boolQuery()
                    .must(QueryBuilders
                            .queryStringQuery(header)
                            .defaultOperator(QueryStringQueryBuilder.Operator.OR)
                            .field("header"))
            val searchQuery: SearchQuery = NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .build()
            return elasticsearchTemplate.queryForList(searchQuery, Article::class.java)
        } catch (e : RuntimeException) { throw ServiceException() }
    }

//    @Async
    override fun save(article: Article?) {
        if(article === null) {
            LOGGER.debug("Param Article is null", NullPointerException())
            throw ServiceException(NullPointerException())
        }
        try {
            elasticsearchTemplate.putMapping(Article::class.java)
            elasticSearchDao.save(article)
        } catch (e : RuntimeException) { throw ServiceException(e.cause)}
    }

//    @Async
    override fun save(list: MutableList<Article>?) {
        if(list === null) {
            LOGGER.debug("Param MutableList<Article> is null", NullPointerException())
            throw ServiceException(NullPointerException())
        }
        try {
            elasticSearchDao.save(list)
        } catch (e : RuntimeException) { throw ServiceException(e.cause)}
    }
}