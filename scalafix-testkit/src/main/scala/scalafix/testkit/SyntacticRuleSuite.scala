package scalafix.testkit

import org.scalatest.Tag
import scala.meta._
import scalafix.syntax._
import scalafix.v0._
import scalafix.internal.config.ScalafixConfig
import org.scalatest.funsuite.AnyFunSuiteLike

/** Utility to unit test syntactic rules
 *
 * @param rule the default rule to use from `check`/`checkDiff`.
 */
class SyntacticRuleSuite(rule: Rule = Rule.empty)
    extends AnyFunSuiteLike
    with DiffAssertions {

  def check(name: String, original: String, expected: String): Unit = {
    check(rule, name, original, expected)
  }

  def check(
      rule: Rule,
      name: String,
      original: String,
      expected: String
  ): Unit = {
    check(rule, name, original, expected, Seq(): _*)
  }

  def check(
      rule: Rule,
      name: String,
      original: String,
      expected: String,
      testTags: Tag*
  ): Unit = {
    test(name, testTags: _*) {
      import scala.meta._
      val obtained = rule.apply(Input.String(original))
      assertNoDiff(obtained, expected)
    }
  }

  def checkDiff(original: Input, expected: String): Unit = {
    checkDiff(rule, original, expected, Seq(): _*)
  }

  def checkDiff(original: Input, expected: String, testTags: Tag*): Unit = {
    checkDiff(rule, original, expected, testTags: _*)
  }

  def checkDiff(rule: Rule, original: Input, expected: String): Unit = {
    checkDiff(rule, original, expected, Seq(): _*)
  }

  def checkDiff(
      rule: Rule,
      original: Input,
      expected: String,
      testTags: Tag*
  ): Unit = {
    test(original.label, testTags: _*) {
      val dialect = ScalafixConfig.default.parser.dialectForFile("Source.scala")
      val ctx = RuleCtx(dialect(original).parse[Source].get)
      val obtained = rule.diff(ctx)
      assertNoDiff(obtained, expected)
    }
  }
}
