package app.flow.util.result

data class ResultCallBack<T>(var status: Status, var data: T?, var message: String?) {
    companion object {
        fun <T> success(data: T): ResultCallBack<T> =
            ResultCallBack(
                status = Status.SUCCESS,
                data = data,
                message = null
            )

        fun <T> error(message: String): ResultCallBack<T> =
            ResultCallBack(
                status = Status.ERROR,
                data = null,
                message = message
            )
    }
}

enum class Status {
    SUCCESS,
    ERROR
}