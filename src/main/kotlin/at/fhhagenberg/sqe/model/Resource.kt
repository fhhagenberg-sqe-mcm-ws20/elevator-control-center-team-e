package at.fhhagenberg.sqe.model

data class Resource<TData>(
        val status: Status,
        val data: TData?,
        val error: Error? = null
) {

    companion object {
        @JvmStatic
        fun <TData> loading(data: TData?): Resource<TData> {
            return Resource(Status.LOADING, data)
        }

        @JvmStatic
        fun <TData> success(data: TData?): Resource<TData> {
            return Resource(Status.SUCCESS, data)
        }

        @JvmStatic
        fun <TData> error(error: Error?): Resource<TData> {
            return Resource(Status.ERROR, null, error)
        }
    }
}