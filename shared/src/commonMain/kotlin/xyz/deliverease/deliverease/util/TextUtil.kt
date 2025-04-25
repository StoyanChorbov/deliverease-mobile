package xyz.deliverease.deliverease.util

fun String.toPascalCase(): String =
    this.first() + this.subSequence(1..<this.length).toString().lowercase()

fun <T : Enum<T>> T.toPascalCase(): String =
    name.toPascalCase()