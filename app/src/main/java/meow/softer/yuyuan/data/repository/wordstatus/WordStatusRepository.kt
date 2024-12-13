package meow.softer.yuyuan.data.repository.wordstatus

import meow.softer.yuyuan.data.Result
import meow.softer.yuyuan.data.local.dao.WordStatusDao
import meow.softer.yuyuan.data.local.entiity.Word
import meow.softer.yuyuan.data.local.entiity.WordStatus
import meow.softer.yuyuan.data.repository.runInBackground
import java.time.ZonedDateTime
import javax.inject.Inject


class WordStatusRepository @Inject constructor(
    private val wordStatusDao: WordStatusDao
) : IWordStatusRepository {

    override suspend fun getAllWordStatus(): Result<List<WordStatus>> {
        return runInBackground { wordStatusDao.getAll() }
    }

    override suspend fun getWordStatusByUser(userId: Int): Result<List<WordStatus>> {
        return runInBackground { wordStatusDao.getAllByUserId(userId) }
    }


    override suspend fun getWordStatusByWordId(wordId: Int, userId: Int): Result<WordStatus> {
        return runInBackground { wordStatusDao.getByWordId(wordId, userId) }
    }

    override suspend fun getWordStatusById(id: Int, userId: Int): Result<WordStatus> {
        return runInBackground { wordStatusDao.getById(id, userId) }
    }

    override suspend fun getLearntList(userId: Int): Result<List<WordStatus>> {
        return runInBackground { wordStatusDao.getLearnt(userId) }
    }

    override suspend fun getStarredList(userId: Int): Result<List<WordStatus>> {
        return runInBackground { wordStatusDao.getStarred(userId) }
    }

    override suspend fun getLearntListByDate(
        date: ZonedDateTime,
        userId: Int
    ): Result<List<WordStatus>> {
        return runInBackground { wordStatusDao.getLearntByDate(date, userId) }
    }

    override suspend fun getLearntListByInterval(
        start: ZonedDateTime,
        end: ZonedDateTime,
        userId: Int
    ): Result<List<WordStatus>> {
        return runInBackground { wordStatusDao.getLearntByInterval(start, end, userId) }
    }

    override suspend fun toggleStarred(word: Word, userId: Int): Result<Unit> {
        return toggleStarredByWordId(word.id, userId)
    }


    override suspend fun toggleStarred(wordStatus: WordStatus): Result<Unit> {
        return toggleStarredByStatusId(wordStatus.id)
    }


    override suspend fun toggleStarredByStatusId(wordStatusId: Int): Result<Unit> {
        return runInBackground { wordStatusDao.toggleStarredByStatusId(wordStatusId) }
    }

    override suspend fun toggleStarredByWordId(wordId: Int, userId: Int): Result<Unit> {
        return runInBackground { wordStatusDao.toggleStarred(wordId, userId) }
    }

    override suspend fun setLearntByWordId(wordId: Int, userId: Int): Result<Unit> {
        return runInBackground {
            wordStatusDao.setLearntByWordId(
                wordId, userId, dateTime = ZonedDateTime.now()
            )
        }
    }

    override suspend fun setLearnt(word: Word, userId: Int): Result<Unit> {
        return setLearntByWordId(word.id, userId)
    }

    override suspend fun setLearnt(wordStatus: WordStatus): Result<Unit> {
        return setLearntByStatusId(wordStatus.id)
    }


    override suspend fun setLearntByStatusId(statusId: Int): Result<Unit> {
        return runInBackground {
            wordStatusDao.setLearntByStatusId(
                statusId, dateTime = ZonedDateTime.now()
            )
        }
    }

    override suspend fun getNewWordList(limit: Int?, userId: Int): Result<List<WordStatus>> {
        return runInBackground {
            if (limit == null) {
                wordStatusDao.getAllNew(userId = userId)
            } else {
                wordStatusDao.getNewWithLimit(limit = limit, userId = userId)
            }

        }
    }

    override suspend fun getRandomNewWordIds(limit: Int, userId: Int): Result<List<Int>> {
        return runInBackground {
            if(limit <= 0){
               throw IllegalArgumentException("limit must be positive Int.")
            }
            wordStatusDao.getRandomNewWordIds(limit = limit, userId = userId)
        }
    }

    override suspend fun getRandomNewWordIds(
        bookId: Int,
        limit: Int,
        userId: Int
    ): Result<List<Int>> {
        return runInBackground {
            if(bookId <= 0 || limit <= 0){
                throw IllegalArgumentException("bookId and limit must be positive Int.")
            }
            wordStatusDao.getRandomNewWordIdsByBook(bookId, limit, userId)
        }
    }
}