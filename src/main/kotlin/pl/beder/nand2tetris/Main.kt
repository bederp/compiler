package pl.beder.nand2tetris

import java.io.File

fun main(args: Array<String>) {
    val parser = Parser()
    val writer = Writer()

    val bufferedReader = File(args[0]).bufferedReader()
    var line =  bufferedReader.readLine()
    while (line != null) {
        val command = parser.parse(line)
        writer.emit(command)
        line = bufferedReader.readLine()
    }

}

data class AST (val command: CommandType, val arg1: String, val arg2: String)

enum class CommandType {
    EMPTY, COMMENT, C_ARITHMETIC, C_PUSH, C_POP, C_LABEL, C_GOTO, C_IF, C_FUNCTION, C_RETURN, C_CALL
}
