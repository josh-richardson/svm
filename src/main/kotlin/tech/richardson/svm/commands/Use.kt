package tech.richardson.svm.commands

import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess
import org.web3j.sokt.SolcInstance
import org.web3j.sokt.SolcRelease
import tech.richardson.svm.Constants
import tech.richardson.svm.settings.Settings

class Use(private val settings: Settings) : Command {
    val path = Paths.get(System.getProperty("user.dir"), ".svmrc").toFile()

    override fun matches(arg: String, len: Int): Boolean {
        return arg == "use" && (len == 1 || (path.exists() && path.isFile))
    }

    override fun execute(args: List<String>): String {
        val versionToUse = if (path.exists())
            path.readText().trim()
        else settings.aliases.getOrDefault(
            args.first().trim(),
            args.first().trim()
        )
        val instance = SolcInstance(SolcRelease(versionToUse), Constants.SVM_PATH)
        if (instance.installed()) {
            settings.lastUsed = versionToUse
            val instanceDirectoryName = instance.solcFile.parent

            val path = "export PATH=$instanceDirectoryName" + File.pathSeparator + System.getenv("PATH")
                .split(File.pathSeparator)
                .filter { !it.matches(Constants.PATH_MATCH_REGEX) }.joinToString(File.pathSeparator)
            if (Constants.TEMP_FILE?.exists() == true) {
                Constants.TEMP_FILE.writeText(path)
            } else {

                println(
                    "Attempted to use a solidity version but did not receive a temporary file to write to. " +
                            "Invocation of sokt should occur from the command-line wrapper function rather than by directly invoking the executable."
                )
                println("The following should be exported: $path")
                exitProcess(1)
            }

            return "Version ${instance.solcRelease.version} activated."
        }

        return "The version ${instance.solcRelease.version} is not installed."
    }
}
