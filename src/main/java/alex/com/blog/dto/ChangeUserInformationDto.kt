package alex.com.blog.dto

import alex.com.blog.domaine.User
import alex.com.blog.exception.DtoException
import javax.validation.constraints.Size
import java.io.Serializable

data class ChangeUserInformationDto(@field:Size(min = 1, max=20)
                                    var name : String?,
                                    @field:Size(min = 1, max=20)
                                    var lastName : String?,
                                    var password : String) : Serializable {

    constructor() : this (null,null,"")

    fun change(user: User?) : User {
        if (user === null) throw DtoException(NullPointerException())

        if (name != "") {
            user.name = name
        }
        if (lastName != "") {
            user.lastName = lastName
        }
        return user
    }

    companion object {
        private val serialVersionUID: Long = 4
    }
}