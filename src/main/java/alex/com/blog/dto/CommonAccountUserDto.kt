package alex.com.blog.dto

import alex.com.blog.domaine.User
import alex.com.blog.exception.DtoException
import java.time.LocalDate
import java.time.LocalTime
import java.io.Serializable

data class CommonAccountUserDto(var uid : String,
                                var nick : String,
                                var email : String,
                                var name : String?,
                                var lastName : String?,
                                var creationDate : LocalDate,
                                var creationTime : LocalTime,
                                var avatar : ByteArray?) : Serializable {

    constructor() : this ("","","","","", LocalDate.now(), LocalTime.now(), null)

    fun convertToDto(user: User?) : CommonAccountUserDto {
        if (user === null) throw DtoException("NPE", NullPointerException())
        uid = user.uid
        nick = user.nick
        email = user.email
        name = if(user.name != null) user.name else " "
        lastName = if(user.lastName != null) user.lastName else " "
        creationDate = user.creationDate
        creationTime = user.creationTime
        avatar = if (user.avatars.size > 1) user.avatars.first {it.id != 1L}
                .image else user.avatars[0].image;
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CommonAccountUserDto) return false

        if (uid != other.uid) return false
        if (nick != other.nick) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + nick.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }

    companion object {
        private val serialVersionUID: Long = 6
    }
}
