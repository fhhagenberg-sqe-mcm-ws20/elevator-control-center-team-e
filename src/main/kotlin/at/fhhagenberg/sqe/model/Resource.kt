package at.fhhagenberg.sqe.model

data class Resource<TData>(
        val data: TData?,
        val error: Throwable?,
        val status: Status
) {

    companion object {
        @JvmStatic
        fun <TData> loading(data: TData): Resource<TData> {
            return Resource(data, null, Status.LOADING)
        }

        @JvmStatic
        fun <TData> success(data: TData): Resource<TData> {
            return Resource(data, null, Status.SUCCESS)
        }

        @JvmStatic
        fun <TData> error(error: Throwable?): Resource<TData> {
            return Resource(null, error, Status.ERROR)
        }
    }
}