package build

// scalafix:off
import $ivy.`com.goyeau::mill-scalafix::0.4.2`
import com.goyeau.mill.scalafix.ScalafixModule
import mill._
import mill.scalanativelib.api.ReleaseMode

import scalalib._
import scalanativelib._
// scalafix:on

trait ScriptModule extends ScalaNativeModule with ScalafixModule {
  def scalaVersion = "3.3.4"
  def scalaNativeVersion = "0.5.6"
  def ivyDeps = Agg(ivy"com.github.alexarchambault::case-app::2.1.0-M29")

  object test extends ScalaNativeTests {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest::0.8.4")
    def testFramework = "utest.runner.Framework"
  }
}

// fsp stands for "find scala projects"
object fsp extends ScriptModule {
  def ivyDeps = super.ivyDeps() ++ Agg(ivy"com.lihaoyi::os-lib::0.11.3")
  def mainClass = Some("fsp.Main")
  val executableName = "find-scala-projects"
  def linkRename = T {
    val nativeLinkPath = nativeLink()
    val destPath = nativeLinkPath/".."/executableName
    val move = os.move(nativeLinkPath, destPath)
    destPath
  }
  override def nativeIncrementalCompilation: T[Boolean] = true
  override def releaseMode: T[ReleaseMode] = {
    val isCI = sys.env.getOrElse("CI", "false") match {
      case "true" => true
      case _ => false
    }
    isCI match {
      case true =>  ReleaseMode.ReleaseFull
      case false => ReleaseMode.ReleaseFast
    }
  }
}
