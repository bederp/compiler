package pl.beder.nand2tetris

import pl.beder.nand2tetris.CommandType.*
import java.io.Closeable
import java.io.File
import java.lang.RuntimeException

val ARITHMETIC = listOf("add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not")
val STACK = listOf("push", "pop")
val COMMENT = listOf("//")

class Parser(filename: String) : Iterator<AST>, AutoCloseable, Closeable {

    private val bufferedReader = File(filename).bufferedReader()
    var currentLine: String? = ""

    override fun hasNext(): Boolean {
        skipBlank()
        return currentLine != null
    }

    private fun skipBlank() {
        currentLine = bufferedReader.readLine()
        while (currentLine.isMyBlank()) {
            currentLine = bufferedReader.readLine()
        }
    }

    private fun skipBlankAndComments() {
        currentLine = bufferedReader.readLine()
        while (currentLine.isBlankOrComment()) {
            currentLine = bufferedReader.readLine()
        }
    }

    override fun next(): AST {
        return toCommand(currentLine!!)
    }

    override fun close() {
        bufferedReader.close()
    }

    private fun toCommand(line: String): AST {
        val words = line.split("\\s+".toRegex())

        return when {
            ARITHMETIC.contains(words[0]) -> parseArithmetic(words, line)
            STACK.contains(words[0]) -> parseStack(words, line)
            COMMENT.contains(words[0]) -> parseComment(line)
            else -> throw RuntimeException("Unknown VM command:\n\t$line")
        }
    }

    private fun parseComment(line: String): AST {
        return AST(CommandType.COMMENT, line = line)
    }

    private fun parseStack(words: List<String>, line: String): AST {
        return when (words[0]) {
            "push" -> AST(C_PUSH, words[1], words[2], line)
            "pop" -> AST(C_POP, words[1], words[2], line)
            else -> throw RuntimeException("Shouldn't happen")
        }
    }

    private fun parseArithmetic(words: List<String>, line: String): AST {
        return AST(C_ARITHMETIC, words[0], line = line)
    }

    private fun String?.isBlankOrComment(): Boolean {
        return this?.isBlank() == true || this?.trim()?.startsWith("//") == true
    }

    private fun String?.isMyBlank(): Boolean {
        return this?.isBlank() == true
    }
}
