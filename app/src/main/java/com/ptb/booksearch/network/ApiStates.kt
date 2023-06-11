package com.ptb.booksearch.network

data class ApiState<out T>(
    val status: Status,
    val data: T?,
    val error: Error?,
) {

    companion object {

        //In case of Success,set status as Success and data as the response
        fun <T> success(data: T?): ApiState<T> {
            return ApiState(Status.SUCCESS, data, null)
        }

        //In case of failure ,set state to Error ,add the error ,set data to null
        fun <T> error(error: Error?): ApiState<T> {
            return ApiState(Status.ERROR, null, error)
        }

        //When the call is loading set the state as Loading and rest as null
        fun <T> loading(data: T? = null): ApiState<T> {
            return ApiState(Status.LOADING, data, null)
        }

        //Use this state as default init and rest as null
        fun <T> init(): ApiState<T> {
            return ApiState(Status.INIT, null, null)
        }

    }

}

//An enum to store the current state of api call
enum class Status {
    INIT,
    LOADING,
    SUCCESS,
    ERROR
}