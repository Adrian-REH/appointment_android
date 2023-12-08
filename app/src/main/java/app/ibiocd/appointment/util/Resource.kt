package app.ibiocd.appointment.util


sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}


sealed class ApiResource<T>(val result: T? = null,
                            val message: String? = null) {
    class Success<T>(result: T) : ApiResource<T>(result)
    class Error<T>(message: String, result: T? = null) : ApiResource<T>(result, message)
    class Loading<T>(result: T? = null) : ApiResource<T>(result)
}