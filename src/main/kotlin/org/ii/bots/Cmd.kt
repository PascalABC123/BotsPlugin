package org.ii.bots

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Cmd(
    val name: String,
    val args: String,
    val minArgs: Int,
    val permission: String,
    val aliases: Array<String>
)
