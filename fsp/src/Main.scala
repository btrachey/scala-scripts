package fsp

import caseapp.*
import os.Path

import scala.util.Try

object Options {
  implicit lazy val parser: Parser[Options] = Parser.derive
  implicit lazy val help: Help[Options] = Help.derive
}
@AppName("Find Scala Projects")
@ProgName("find-scala-projects")
case class Options(
    @HelpMessage("Directory at which to start search; default $HOME.")
    @ValueDescription("path name")
    @Name("d")
    startDir: Option[String] = None,
    @HelpMessage(
      "Limit the depth of folders to search."
    )
    @ValueDescription("number")
    @Name("m")
    maxFolderDepth: Int
)

object MyApp extends Command[Options] {
  def run(config: Options, rem: RemainingArgs): Unit = {
    val startDir = config.startDir
    val maxFolderDepth = config.maxFolderDepth
    val scalaProjectIdentifierFiles =
      Seq("build.sbt", "build.mill", "build.sc", "project.scala")
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
      .filter(p => scalaProjectIdentifierFiles.contains(p.last))
    val projectDirs = projectFiles.map(_ / "..")
    println(projectDirs.mkString("\n"))
  }
}

object Main extends CommandsEntryPoint {
  def progName = "find-scala-projects"
  def commands = Seq()
  override def defaultCommand = Some(MyApp)
  override def enableCompleteCommand = true
  override def enableCompletionsCommand = true
}
