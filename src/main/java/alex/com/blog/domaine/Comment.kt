package alex.com.blog.domaine

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
data class Comment(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id : Long? = null,

                   @field: NotNull
                   @field: Size(min = 2, max = 500)
                   var content : String? = null,

                   @ManyToOne(fetch = FetchType.LAZY)
                   @JoinColumn(name = "user_id", referencedColumnName = "id")
                   var user : User? = null,

                   @ManyToOne(fetch = FetchType.LAZY)
                   @JoinColumn(name = "article_id", referencedColumnName = "id")
                   var article : Article? = null,

                   @Basic @Column(name = "creation_date")
                   var creationDate : LocalDate = LocalDate.now(),

                   @Column(name = "parent_id")
                   var parentComment : Long? = null) : Serializable {

    companion object {
        private val serialVersionUID: Long = 2
    }
}