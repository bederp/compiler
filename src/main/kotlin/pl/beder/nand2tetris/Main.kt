package pl.beder.nand2tetris

fun main(args: Array<String>) {
    val parser = Parser(args[0])
    val writer = Writer(args[0])

    parser.use {
        writer.use {
            while (parser.hasNext()) {
                val line = parser.next()
                println(line)
                writer.emit(line)
            }
        }
    }
}



