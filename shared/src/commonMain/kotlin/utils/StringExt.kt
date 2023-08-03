package utils

fun areNotEmpty(vararg values: String): Boolean {
    return values.all { it.isNotBlank() }
}

fun String.toLongOrDef(defaultValue: Long = 0L): Long {
    return this.toLongOrNull() ?: defaultValue
}
