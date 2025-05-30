package meow.softer.yuyuan.data.repository.dailyplanword

import meow.softer.yuyuan.utils.debug
import meow.softer.yuyuan.data.Result
import meow.softer.yuyuan.data.local.dao.DailyPlanWordDao
import meow.softer.yuyuan.data.local.entiity.DailyPlanWord
import meow.softer.yuyuan.data.repository.runBackgroundIO
import javax.inject.Inject

private val MyTag = DailyPlanWordRepository::class.simpleName

class DailyPlanWordRepository @Inject constructor(
    private val dailyPlanWordDao: DailyPlanWordDao
) : IDailyPlanWordRepository {
    override suspend fun getAll(): Result<List<DailyPlanWord>> {
        return runBackgroundIO { dailyPlanWordDao.getAll() }
    }

    override suspend fun getAllByPlanId(dailyPlanId: Int): Result<List<DailyPlanWord>> {
        return runBackgroundIO { dailyPlanWordDao.getAllByPlanId(dailyPlanId) }
    }

    override suspend fun addToPlan(planId: Int, wordId: Int): Result<Unit> {
        return runBackgroundIO {
            dailyPlanWordDao.insert(
                DailyPlanWord(
                    dailyPlanId = planId,
                    wordId = wordId
                )
            )
        }
    }

    override suspend fun addToPlan(planId: Int, wordIds: List<Int>): Result<Unit> {
        return runBackgroundIO {
            val dailyPlanWords = wordIds.map {
                DailyPlanWord(
                    dailyPlanId = planId,
                    wordId = it
                )
            }
            debug("dailyplan words: $dailyPlanWords", MyTag)
//            dailyPlanWordDao.insertAll(dailyPlanWords)
            dailyPlanWords.forEach { dailyPlanWordDao.insert(it.dailyPlanId, it.wordId) }
        }
    }

    override suspend fun clearAll(): Result<Unit> {
        return runBackgroundIO {
            dailyPlanWordDao.deleteAll()
        }
    }

    override suspend fun clearByPlanId(dailyPlanId: Int): Result<Unit> {
        return runBackgroundIO {
            dailyPlanWordDao.deleteByPlanId(dailyPlanId)
        }
    }


}