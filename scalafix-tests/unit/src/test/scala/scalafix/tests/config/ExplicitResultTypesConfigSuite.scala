package scalafix.tests.config

import org.scalatest.funsuite.AnyFunSuite
import scalafix.internal.reflect.ClasspathOps
import scalafix.internal.rule._
import scalafix.v1.Configuration

class ExplicitResultTypesConfigSuite extends AnyFunSuite {
  test("Unsupported Scala version") {
    val scalaVersion = "2.12.0"
    val classpath = ClasspathOps.thisClasspath.entries
    val config = new ExplicitResultTypes().withConfiguration(
      Configuration()
        .withScalaVersion(scalaVersion)
        .withScalacClasspath(classpath)
    )
    assert(config.isNotOk)
  }
}
