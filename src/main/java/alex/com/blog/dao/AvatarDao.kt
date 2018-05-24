package alex.com.blog.dao

import alex.com.blog.domaine.Avatar
import alex.com.blog.domaine.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AvatarDao : JpaRepository <Avatar, Long> {

    fun findByUrl(url : String) : Avatar
    fun findByUser(users : MutableList<User>?) : Avatar

    @Modifying
    @Query("delete from Avatar a where a.id = :id")
    fun deleteAbandonedAvatarsScheduler(@Param("id") id : Long)

}