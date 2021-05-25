import java.io.File
import java.util.concurrent.TimeUnit

fun String.runCommand(
    workingDir: File = File("."),
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS
): String? = runCatching {
    ProcessBuilder("\\s".toRegex().split(this))
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start().also { it.waitFor(timeoutAmount, timeoutUnit) }
        .inputStream.bufferedReader().readText()
}.onFailure { it.printStackTrace() }.getOrNull()

data class CommitInfo(val hash: String) {
    lateinit var author: String
    lateinit var date: String
    lateinit var name: String

    init {
        val command = "git show $hash".runCommand()

        if (command != null) {
            val process = command.split("\n")

            /* Example of "git show <hash>"
            commit <hash> (HEAD -> master, origin/master)
            Author: <username> <<email>>
            Date:   <weekday> <month> <monthday> <time> <year> -0500
                <commit-name>
             */

            author = process[1]
            date = process[2]
            name = process[4].substring(4)
        } else { author = name = date = "" }
    }
}

fun generateDescription(info: CommitInfo) {
    return """## Commit information:
        Name: ${info.name}
        Author: ${info.author}
        [Filetree](https://github.com/NiChrosia/Acceleration/tree/${info.hash})
        [Commit](https://github.com/NiChrosia/Acceleration/commit/${info.hash})""".trimIndent()
}

val file = File.createNewFile("output.json")

file.writeText(
    generateDescription(CommitInfo(args[0]))
)