package find

import caseapp.*
import os.Path

import scala.util.Try

object Main extends CommandsEntryPoint {
  object Scala extends FindCommand {
    override def name = "scala"
    val projectIdentifierFiles = Definitions.Scala.projectIdentifierFiles
  }
  object Python extends FindCommand {
    override def name = "python"
    val projectIdentifierFiles = Definitions.Python.projectIdentifierFiles
  }
  def progName = "find-projects"
  def commands = Seq(Scala, Python)
  override def enableCompleteCommand = true
  override def enableCompletionsCommand = true
}
