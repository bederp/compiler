package pl.beder.nand2tetris

import pl.beder.nand2tetris.CommandType.COMMENT
import pl.beder.nand2tetris.CommandType.C_PUSH
import java.io.Closeable
import java.io.File

class Writer(filename: String) : Closeable {

    private val bufferedWriter = File("${trimExtension(filename)}.asm").bufferedWriter()
    private var previousWasComment = false

    fun emit(ast: AST) {
        newlineAfterCommentsSection(ast)
        commandAsComment(ast)
        actualCode(ast)
    }

    private fun commandAsComment(ast: AST) {

        bufferedWriter.write(ast.toString())
    }

    private fun newlineAfterCommentsSection(ast: AST) {
        if (previousWasComment && ast.command != COMMENT) {
            bufferedWriter.newLine()
            previousWasComment = false
        }
        if (ast.command == COMMENT)
            previousWasComment = true
    }

    private fun actualCode(command: AST) {
        when (command.command) {
            COMMENT -> {}
            C_PUSH -> handlePush(command)
            else -> {}
        }
        bufferedWriter.newLine()
    }

    private fun handlePush(command: AST) {
        when (command.arg1) {
        }
    }

    override fun close() {
        bufferedWriter.close()
    }

    private fun trimExtension(filename: String) = filename.substring(0, filename.lastIndexOf('.'))

}
