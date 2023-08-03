package utils

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Long?.orZero(): Long {
    return this ?: 0
}

fun Float?.orZero(): Float {
    return this ?: 0.0f
}
