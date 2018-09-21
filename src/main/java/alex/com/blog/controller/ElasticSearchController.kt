package alex.com.blog.controller

import alex.com.blog.annotations.Log
import alex.com.blog.domaine.Article
import alex.com.blog.exception.ServiceException
import alex.com.blog.service.ArticleService
import alex.com.blog.service.ElasticSearchService
import alex.com.blog.util.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.annotation.PostConstruct

@Controller
open class ElasticSearchController @Autowired constructor(private var elasticSearchService: ElasticSearchService,
                                                          private var elasticsearchTemplate : ElasticsearchOperations,
                                                          private var articleService : ArticleService) {

    @Log
    private val LOGGER: Logger? = null

    @PostConstruct
    fun initElasticSearch() {
        try {
            elasticsearchTemplate.putMapping(Article::class.java)
            elasticSearchService.save(articleService.findAll())
        } catch (e : ServiceException) {
            LOGGER!!.debug(" elasticSearchService.save(articleService.findAll.  " + e.message)
        }
    }

    @PostMapping(value = ["/search","/search/{header}"])
    @ResponseBody fun findArticleBHeaderLike(@PathVariable(required = false) header : String?) : MutableList<Article> {
        try {
            return if (header !== null) elasticSearchService.findByHeaderStartsWith(header)
            else return mutableListOf()
        } catch (e : ServiceException) {
            LOGGER!!.debug("elasticSearchService.findByHeaderStartsWith result an exception.  " + e.message)
            throw RuntimeException()
        }
    }
}
