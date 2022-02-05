package pl.beder.nand2tetris

import pl.beder.nand2tetris.CommandType.*
import pl.beder.nand2tetris.CommandType.COMMENT
import pl.beder.nand2tetris.MemorySegment.CONSTANT
import java.io.Closeable
import java.io.File

class Writer(filename: String) : Closeable {

    private val bufferedWriter = File("${trimExtension(filename)}.asm").bufferedWriter()
    private var previousWasComment = false

    fun emit(ast: AST) {
        checkForCommentsSection(ast)
        emitLineAsComment(ast)
        emmitAsm(ast)
    }

    private fun emitLineAsComment(ast: AST) {
        writeln(ast.toString())
    }

    private fun checkForCommentsSection(ast: AST) {
        // Any 2 or more lines of comment will emit newline after them
        if (previousWasComment && ast.command != COMMENT) {
            newLine()
            previousWasComment = false
        }
        if (ast.command == COMMENT)
            previousWasComment = true
    }

    private fun emmitAsm(command: AST) {
        when (command.command) {
            COMMENT -> {}
            C_PUSH -> handlePush(command).also { newLine() }
            C_POP -> handlePop(command).also { newLine() }
            else -> {}
        }
    }

    private fun handlePush(command: AST) {
        when (command.arg1) {
            CONSTANT.segment -> pushConstant(command)
            else -> pushSegment(command.arg1!!, command.arg2!!)
        }
    }

    private fun pushSegment(segment: String, offset: String) {

    }

    private fun pushConstant(command: AST) {
        writeln(
            "@${command.arg2!!}",
            "D=A",
            "@SP",
            "A=M",
            "M=D",
            "@SP",
            "M=M+1"
        )
    }

    private fun handlePop(command: AST) {
        writeln(
            "@${MemorySegment.forName(command.arg1!!)}",
            "D=M",
            "@${command.arg2!!}",
            "D=D+M",
            "@SP",
            "M=M-1",
            "A=M",
            "D=D+A",
            "A=D-A",
            "D=D-A",
            "M=D"
        )
    }

    private fun swapRegisters() {
        writeln(
            "D=D+A",
            "A=D-A",
            "D=D-A",
        )
    }

    private fun writeln(vararg lines: String) {
        for (line in lines) {
            bufferedWriter.write(line)
            newLine()
        }
    }

    private fun newLine() {
        bufferedWriter.newLine()
    }

    override fun close() {
        bufferedWriter.close()
    }

    private fun trimExtension(filename: String) = filename.substring(0, filename.lastIndexOf('.'))
}
