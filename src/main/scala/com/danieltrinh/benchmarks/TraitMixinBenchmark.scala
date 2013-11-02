package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.concurrent.Future
import scala.util.{ Try, Failure, Success}
import scala.util.control.NoStackTrace

class TraitMixinBenchmark extends SimpleScalaBenchmark {
  import TraitMixinBenchmark._

  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _

  override def setUp() {
    array = new Array(length)
  }
  def timeStaticTraits(reps: Int): StaticTraits = repeat(reps) {
    var i = 0

    var result: StaticTraits = null
    while(i < array.length) {
      result = StaticTraits("hello world")
      i = i + 1
    }

    result
  }

  def timeInstanceTraits(reps: Int): DummyClass = repeat(reps) {
    var i = 0

    var result: DummyClass = null
    while(i < array.length) {
      result = new DummyCaseClass("hello world") with One with Two with Three
      i = i + 1
    }

    result
  }
}

object TraitMixinBenchmark {
  class DummyClass(something: String)

  case class DummyCaseClass(something: String) extends DummyClass(something)

  trait One {
    val one = 1
  }

  trait Two {
    val two = 2
  }

  trait Three {
    def three = 3
  }

  case class StaticTraits(s: String) 
    extends DummyClass(s)
    with One 
    with Two 
    with Three  
}