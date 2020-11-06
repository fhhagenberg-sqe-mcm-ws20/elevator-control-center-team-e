package at.fhhagenberg.sqe.util

object EnumUtils {
    @JvmStatic
    inline fun <reified T : Enum<*>> valOf(filter: (T) -> Boolean): T? {
        return enumValues<T>().firstOrNull(filter)
    }
}