package mexan1ck.log

@Suppress("unused")
object Log {
    private fun log(logLevel: LogLevel, prefix: String, message: String) {
        if (LogConfig.logLevel.ordinal <= logLevel.ordinal) println("[$prefix]: $message")
    }
    fun info(message: String) = log(LogLevel.INFO, "INFO", message)
    fun warn(message: String) = log(LogLevel.WARNING, "WARNING", message)
    fun error(message: String) = log(LogLevel.ERROR, "ERROR", message)
    fun debug(message: String) = log(LogLevel.DEBUG, "DEBUG", message)
    fun debug(e: Exception) = if (LogConfig.logLevel.ordinal <= LogLevel.DEBUG.ordinal) e.printStackTrace() else {}
    fun incoming(message: String) = log(LogLevel.DEBUG, "▷", message)
    fun outgoing(message: String) = log(LogLevel.DEBUG, "◄", message)
}