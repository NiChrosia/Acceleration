import java.io.File

data class CommitInfo(val hash: String) {
    lateinit var author: String
    lateinit var date: String
    lateinit var name: String

    init {
        val process = run("git show $hash").split("\n")

        /* Example of "git show <hash>"
        commit <hash> (HEAD -> master, origin/master)
        Author: <username> <<email>>
        Date:   <weekday> <month> <monthday> <time> <year> -0500
            <commit-name>
         */

        author = process[1]
        date = process[2]
        name = process[4].substring(4)
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
    generateDescription(args[0])
)