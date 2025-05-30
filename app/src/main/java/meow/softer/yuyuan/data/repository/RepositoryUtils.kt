package meow.softer.yuyuan.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import meow.softer.yuyuan.data.Result
import meow.softer.yuyuan.utils.debug

/**
 * Specifically for repository's IO operations
 * Handles YuResult automatically with coroutine
 *
 * With Dispatchers.IO context
 */
suspend fun <T> runBackgroundIO(block: suspend () -> T): Result<T> {
    return try {
        val result = withContext(Dispatchers.IO) { block() }
        Result.Success(result)
    } catch (e: Exception) {
        debug("Run in background error")
        debug(e.stackTraceToString())
        Result.Error(e)
    }
}
