package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.concurrent.Future
import scala.util.{ Try, Failure, Success}
import scala.util.control.NoStackTrace

class DefaultArgsVsSingletonADT extends SimpleScalaBenchmark {
  import DefaultArgsVsSingletonADT._

  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _

  override def setUp() {
    array = new Array(length)
  }
  def timeDefaultArguments(reps: Int): String = repeat(reps) {
    var i = 0

    var result = 0

    while(i < array.length) {
      result = DefaultArgumentsConfig().hashCode
      i = i + 1
    }

    result.toString
  }

  def timeSingletonADT(reps: Int): String = repeat(reps) {
    var i = 0

    var result = 0

    while(i < array.length) {
      result = SingletonConfig.hashCode
      i = i + 1
    }

    result.toString
  }
}

object DefaultArgsVsSingletonADT {

  object Config {
    val one = 1
    val two = 2
    val three = 3
  }

  trait Config {
    val one: Int
    val two: Int
    val three: Int
  }

  case class DefaultArgumentsConfig(
    one: Int = Config.one,
    two: Int = Config.two,
    three: Int = Config.three) extends Config

  case object SingletonConfig extends Config {
    val one: Int = 1
    val two: Int = 2
    val three: Int = 3
  }
}