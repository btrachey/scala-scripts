package object find {
  import upickle.default.{ReadWriter => RW, macroRW}

  object Project {
    implicit val rw: RW[Project] = macroRW
  }
  case class Project(path: String, buildFile: String)
  object Definitions {
    trait FileIdentifiers {
      val projectIdentifierFiles: Seq[String]
    }
    object Scala extends FileIdentifiers {
      val projectIdentifierFiles: Seq[String] =
        Seq("build.sbt", "build.mill", "build.sc", "project.scala")
    }
    object Python extends FileIdentifiers {
      val projectIdentifierFiles: Seq[String] =
        Seq("pyproject.toml")
    }
  }
}
