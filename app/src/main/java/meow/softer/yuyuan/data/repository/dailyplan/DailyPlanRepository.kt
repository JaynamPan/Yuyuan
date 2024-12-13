package meow.softer.yuyuan.data.repository.dailyplan

import meow.softer.yuyuan.data.Result
import meow.softer.yuyuan.data.local.dao.DailyPlanDao
import meow.softer.yuyuan.data.local.entiity.DailyPlan
import meow.softer.yuyuan.data.repository.runInBackground
import java.time.ZonedDateTime
import javax.inject.Inject

class DailyPlanRepository @Inject constructor(
    private val dailyPlanDao: DailyPlanDao
) : IDailyPlanRepository {
    override suspend fun getAllPlans(): Result<List<DailyPlan>> {
        return runInBackground { dailyPlanDao.getAll() }
    }

    override suspend fun getPlansByUserId(userId: Int): Result<List<DailyPlan>> {
        return runInBackground { dailyPlanDao.getByUserId(userId) }
    }

    override suspend fun getPlanById(id: Int): Result<DailyPlan> {
        return runInBackground { dailyPlanDao.getById(id) }
    }

    override suspend fun create(date: ZonedDateTime, userId: Int): Result<DailyPlan> {
        val dailyPlan = DailyPlan(
            userId = userId,
            date = date
        )
        val id = dailyPlanDao.insert(dailyPlan)
        return if (id > 0) {
            Result.Success(dailyPlan)
        } else {
            Result.Error(IllegalStateException("insert returned invalid id "))
        }
    }

    override suspend fun clearAll(): Result<Unit> {
        return runInBackground { dailyPlanDao.deleteAll() }
    }

    override suspend fun clearByUserId(userId: Int): Result<Unit> {
        return runInBackground { dailyPlanDao.deleteByUserId(userId) }
    }
}