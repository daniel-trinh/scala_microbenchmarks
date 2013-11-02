package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.concurrent.Future
import scala.util.{ Try, Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

class ExceptionObjectCreationBenchmark extends SimpleScalaBenchmark {

  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _


  override def setUp() {
    array = new Array(length)
  }

  def timeExceptionCreation(reps: Int): Throwable = repeat(reps) {
    var i = 0
    var result = new RuntimeException(s"$i")
    while(i < array.length) {
      result = new RuntimeException(s"$i")
      i = i + 1
    }
    result

  }

  def timeDummyExceptionADTCreation(reps: Int): FakeExceptionADT = repeat(reps) {
    var i = 0
    var result = FakeException(i)
    while(i < array.length) {
      result = FakeException(i)
      i = i + 1
    }
    result
  }

  sealed trait FakeExceptionADT
  case class FakeException(i: Int) extends FakeExceptionADT 
}