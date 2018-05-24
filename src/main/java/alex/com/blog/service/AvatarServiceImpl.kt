package alex.com.blog.service

import alex.com.blog.dao.AvatarDao
import alex.com.blog.domaine.Avatar
import alex.com.blog.domaine.User
import alex.com.blog.exception.ServiceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException


@Service
open class AvatarServiceImpl @Autowired constructor(private var avatarDao: AvatarDao) : AvatarService {

    @Transactional(readOnly = true)
    override fun findByUser(users : MutableList<User>?): Avatar {
        if (users === null) throw ServiceException(NullPointerException())
        try {
            return avatarDao.findByUser(users)
        } catch (e : RuntimeException) {
            throw ServiceException(e.cause)
        }
    }

    @Transactional
    override fun deleteAvatar(avatar: Avatar?) {
        if (avatar === null) throw ServiceException( NullPointerException())
        try {
            avatarDao.delete(avatar)
        } catch (e : RuntimeException) { throw ServiceException(e.cause) }
    }

    @Transactional
    override fun addAvatar(avatar: Avatar?) {
        if (avatar === null) throw ServiceException(NullPointerException())
        try {
            avatarDao.save(avatar)
        } catch (e : RuntimeException) { throw ServiceException(e.cause)
        }
    }

    @Transactional(readOnly = true)
    override fun findByUrl(url: String?): Avatar {
        if (url === null) throw ServiceException(NullPointerException())
        try {
            return avatarDao.findByUrl(url)
        } catch (e : RuntimeException) { throw ServiceException(e.cause)
        }
    }

    @Transactional
    override fun deleteAvatarSublist(avatars: MutableList<Avatar>?) {
        if (avatars === null) throw ServiceException(NullPointerException())
        try {
            avatarDao.delete(avatars)
        } catch (e : RuntimeException) { throw ServiceException(e.cause)
        }
    }

    @Transactional(readOnly = true)
    override fun getAvatarById(id: Long?): Avatar {
        if (id === null) throw ServiceException(NullPointerException())
        try {
            return avatarDao.findOne(id) } catch (e : IOException) { throw ServiceException(e.cause)
        }
    }

    @Transactional
    override fun addAvatar(avatars : List<Avatar>?) {
        if (avatars === null) throw ServiceException(NullPointerException())
        try {
            avatarDao.save(avatars) } catch (e : RuntimeException) { throw ServiceException(e.cause)
        }
    }


}