package alex.com.blog.domaine

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.persistence.*

@Entity
data class Avatar (@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                   @JsonIgnore
                   var id : Long?,
                   @JsonIgnore
                   var url : String,
                   @Lob
                   var image : ByteArray?,
                   @JsonIgnore
                   @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "avatars")
                   var user : MutableList<User>?) : Serializable {

    constructor() : this (null, "", null, null)

    companion object {
        private val serialVersionUID: Long = 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Avatar) return false
        if (id != other.id) return false
        if (url != other.url) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + url.hashCode()
        return result
    }
}