package find

import caseapp.*

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
    val projectDirs = projectFiles.map(_ / "..")
    println(projectDirs.mkString("\n"))
  }
}
