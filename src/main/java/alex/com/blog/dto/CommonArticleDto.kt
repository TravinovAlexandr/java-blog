package alex.com.blog.dto

import alex.com.blog.domaine.Article
import alex.com.blog.exception.DtoException
import java.time.LocalDate
import java.io.Serializable

data class CommonArticleDto(var uid : String,
                            var header : String,
                            var content : String,
                            var creationDate : LocalDate?) : Serializable {

    constructor() : this ("" ,"" ,"" , null)

    fun convert(article : Article?) : CommonArticleDto {
        if (article === null) throw DtoException(NullPointerException())

        uid = article.uid
        header = article.header
        content = article.content
        creationDate = article.creationDate
        return this
    }

    companion object {
        private val serialVersionUID: Long = 7
    }
}