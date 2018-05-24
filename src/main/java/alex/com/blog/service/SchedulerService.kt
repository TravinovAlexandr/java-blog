package alex.com.blog.service

interface SchedulerService {
    fun deleteAbandonedAvatarsTask()
    fun deleteExpiredUserAndTokensTask()

}