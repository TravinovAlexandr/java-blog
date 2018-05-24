package alex.com.blog.dto

import alex.com.blog.domaine.Comment
import alex.com.blog.exception.DtoException
import alex.com.blog.util.image.imageload.ImageLoadFactory
import java.time.LocalDate
import java.io.Serializable

data class CommentDto(var userUid : String? = "",
                      var content : String? = "",
                      var creationDate : LocalDate? = null,
                      var nick : String? = "",
                      var avatar : ByteArray? = null) : Serializable {

    fun convertToDto(comment : Comment?) : CommentDto {
        if (comment === null || comment.user?.uid === null) throw DtoException(NullPointerException())

        val imageResize = ImageLoadFactory().getImageResize(0)

        userUid = comment.user?.uid
        content = comment.content
        creationDate = comment.creationDate
        nick = comment.user?.nick
        avatar = imageResize.getDbAvatar(comment.user!!.avatars.first({a -> a.id != 1L }).image, 50)
                ?: imageResize.getDbAvatar(comment.user!!.avatars[0].image,50)
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CommentDto) return false

        if (userUid != other.userUid) return false
        if (nick != other.nick) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userUid?.hashCode() ?: 0
        result = 31 * result + (nick?.hashCode() ?: 0)
        return result
    }

    companion object {
        private val serialVersionUID: Long = 5
    }
}

