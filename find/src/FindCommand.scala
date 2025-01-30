package find

import caseapp.*
import upickle.default.*
import upickle.*

trait FindCommand extends Command[Options] {
  val projectIdentifierFiles: Seq[String]
  def run(config: Options, rem: RemainingArgs): Unit = {
    val startDir = config.startDir
    val maxFolderDepth = config.maxFolderDepth
    val projectFiles = os.walk
      .stream(
        startDir.map(os.home / _).getOrElse(os.home),
        skip = p =>
          os.stat
            .posix(p, followLinks = false)
            .permissions
            .toString()
            .take(4)
            .last != 'r',
        maxDepth = maxFolderDepth
      )
      .filter(p => projectIdentifierFiles.contains(p.last))
    val projects = projectFiles
      .map(p => {
        val path = p / ".."
        val buildFile = p.last
        Project(path.toString, buildFile)
      })
      .toSeq
    println(write(projects))
  }
}
