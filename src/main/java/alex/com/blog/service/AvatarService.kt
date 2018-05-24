package alex.com.blog.service

import alex.com.blog.domaine.Avatar
import alex.com.blog.domaine.User

interface AvatarService {
    fun addAvatar(avatars : List<Avatar>?)
    fun addAvatar(avatar: Avatar?)
    fun getAvatarById(id : Long?) : Avatar
    fun deleteAvatarSublist(avatars : MutableList<Avatar>?)
    fun deleteAvatar(avatar : Avatar?)
    fun findByUrl(url : String?) : Avatar
    fun findByUser(users : MutableList<User>?) : Avatar

}