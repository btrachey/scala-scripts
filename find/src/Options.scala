package find

import caseapp.*

object Options {
  implicit lazy val parser: Parser[Options] = Parser.derive
  implicit lazy val help: Help[Options] = Help.derive
}
case class Options(
    @HelpMessage("Directory at which to start search; default $HOME.")
    @ValueDescription("path name")
    @Name("d")
    startDir: Option[String] = None,
    @HelpMessage(
      "Limit the depth of folders to search; arbitrary default of 5."
    )
    @ValueDescription("number")
    @Name("m")
    maxFolderDepth: Int = 5
)
