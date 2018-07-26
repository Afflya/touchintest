package com.afflyas.touchintest.repository

class RepoResponse<T>(private val status: RepoResponseStatus, private val data: T?) {

    fun getStatus(): RepoResponseStatus {
        return status
    }

    fun getData(): T? {
        return data
    }

    companion object {
        /**
         * Create response instance with data returned by successful response(could be null)
         */
        fun <T> success(data: T?): RepoResponse<T> {
            return RepoResponse(RepoResponseStatus.SUCCESS, data)
        }

        /**
         * Create response instance that indicates that request was failed
         */
        fun <T> error(data: T?): RepoResponse<T> {
            return RepoResponse(RepoResponseStatus.ERROR, data)
        }

        /**
         * Create response instance that indicates that request execution started
         */
        fun <T> loading(): RepoResponse<T> {
            return RepoResponse(RepoResponseStatus.LOADING, null)
        }
    }

}