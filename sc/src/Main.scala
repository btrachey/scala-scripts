package sc

// look at the type of scala project in the directory and run the appropriate build tool command
object Main {
  object ScalaProject {
    val sbt = ScalaProject(Seq("build.sbt"), "sbt")
    val mill = ScalaProject(Seq("build.sc", "build.mill"), "./mill resolve _")
    val cli = ScalaProject(Seq("project.scala"), "scala-cli repl project.scala")
  }
  case class ScalaProject(buildFiles: Seq[String], replCommand: String)
  def main(args: Array[String]): Unit = {
    val projects = os
      .list(os.pwd)
      .map(p => {
        p.last match {
          case sbt if ScalaProject.sbt.buildFiles.contains(sbt) =>
            Some(ScalaProject.sbt)
          case mill if ScalaProject.mill.buildFiles.contains(mill) =>
            Some(ScalaProject.mill)
          case scli if ScalaProject.cli.buildFiles.contains(scli) =>
            Some(ScalaProject.cli)
          case _ => None
        }
      })
      .flatten
      .toSet
    projects.size match {
      case 1 =>
        os.proc(projects.head.replCommand.split(" "))
          .call(stdin = os.Inherit, stdout = os.Inherit, stderr = os.Inherit)
      case _ => sys.exit(1)
    }
  }
}
