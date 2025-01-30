package object find {
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
