package alex.com.blog.dto

import alex.com.blog.domaine.Article
import alex.com.blog.exception.DtoException
import java.time.LocalDate
import java.io.Serializable

data class ArticleWithUserAttachmentDto(var uid : String,
                                        var header : String,
                                        var content : String,
                                        var creationDate : LocalDate?,
                                        var user : CommonAccountUserDto?,
                                        var avatar : ByteArray?) : Serializable {

    constructor() : this ("","","", null, null, null)

    fun convertToDto(article : Article?, userDto : CommonAccountUserDto?, image : ByteArray?) : ArticleWithUserAttachmentDto {
        if (article === null || userDto === null || image === null )
            throw DtoException("NPE", NullPointerException())

        uid = article.uid
        header = article.header
        content = article.content
        creationDate = article.creationDate
        user = userDto
        avatar = image
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArticleWithUserAttachmentDto) return false
        if (uid !== other.uid) return false
        if (header !== other.header) return false
        return true
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + header.hashCode()
        return result
    }

    companion object {
        private val serialVersionUID: Long = 3
    }
}