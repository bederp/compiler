package pl.beder.nand2tetris

import java.io.File
import java.lang.RuntimeException

val ARITHMETIC = listOf("add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not")
val STACK = listOf("push", "pop")

class Parser(filename: String) : Iterator<AST> {


    private val bufferedReader = File(filename).bufferedReader()
    var currentLine: String? = ""

    override fun hasNext(): Boolean {
        skipBlankAndComments()
        return currentLine != null
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

    private fun toCommand(line: String): AST {
        val words = line.split("\\s+".toRegex())

        return when {
            ARITHMETIC.contains(words[0]) -> parseArithmetic(line)
            STACK.contains(words[0]) -> parseStack(line)
            else -> throw RuntimeException("Unknown VM command:\n\t$line")
        }
    }

    private fun parseStack(line: String): AST {
        TODO("Not yet implemented")
    }

    private fun parseArithmetic(line: String): AST {
        TODO("Not yet implemented")
    }
}

private fun String?.isBlankOrComment(): Boolean {
    return this?.isBlank() == true || this?.trim()?.startsWith("//") == true

}




