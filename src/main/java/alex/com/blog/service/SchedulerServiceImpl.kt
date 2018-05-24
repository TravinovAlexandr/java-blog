package alex.com.blog.service

import alex.com.blog.dao.AvatarDao
import alex.com.blog.domaine.Avatar
import alex.com.blog.domaine.EmailVerificationToken
import alex.com.blog.domaine.User
import alex.com.blog.exception.ServiceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
open class SchedulerServiceImpl @Autowired constructor(private var userService : UserService,
                                                       private var avatarDao : AvatarDao,
                                                       private var emailVerificationTokenService : EmailVerificationTokenService) : SchedulerService {

    @Transactional @Scheduled(fixedDelay = 60000*60*24)
    override fun deleteExpiredUserAndTokensTask() {
        try {
            val tokens: MutableList<EmailVerificationToken> = emailVerificationTokenService.findAllTokens()
            for (token: EmailVerificationToken in tokens) {
                val isExpired: Boolean = (token.expireDate.isEqual(LocalDate.now())
                        || token.expireDate.isEqual(LocalDate.now().minusDays(1L)))

                if (!isExpired) {
                    userService.deleteByToken(token)
                }
            }
        } catch (e : RuntimeException) {throw ServiceException(e.cause)}
    }

    @Transactional @Scheduled(fixedDelay = 60000*60*24)
    override fun deleteAbandonedAvatarsTask() {
        try {
            val users : List<User> = userService.findAllUsers()
            val avatars : MutableList<Avatar> = avatarDao.findAll()
            val temp : MutableList<Avatar> = ArrayList()

            for(user : User in users) {
                try {
                    temp.add(user.avatars.first { it -> it.id != 1L })
                } catch (e : RuntimeException) {}
            }
            temp.add(avatarDao.findOne(1L))

            avatars.removeAll(temp)

            for (avatar: Avatar in avatars) {
                avatarDao.deleteAbandonedAvatarsScheduler(avatar.id!!)
            }
        } catch (e : RuntimeException) { throw ServiceException(e.cause)
        }
    }
}