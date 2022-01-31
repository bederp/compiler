package pl.beder.nand2tetris

data class AST(val command: CommandType, val arg1: String? = null, val arg2: String? = null, val line: String) {
    override fun toString(): String {
        return if (command == CommandType.COMMENT)
            line
        else
            "// $line"
    }

}

enum class CommandType {
    EMPTY, COMMENT, C_ARITHMETIC, C_PUSH, C_POP, C_LABEL, C_GOTO, C_IF, C_FUNCTION, C_RETURN, C_CALL
}

enum class Command(val command: String) {
    ADD("add"), SUB("sub"), NEG("neg"), EQ("eq"), GT("gt"), LT("lt"), AND("and"), OR("or"), NOT("not"),
    PUSH("push"), POP("pop");

}

enum class MemorySegment(){
    LCL, ARG, THIS, THAT, CONSTANT, STATIC, TEMP, POINTER
}