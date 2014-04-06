package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.concurrent.Future
import scala.util.{ Try, Failure, Success}
import scala.util.control.NoStackTrace
import scala.concurrent.ExecutionContext.Implicits.global

class MapToSetVsFoldLeft extends SimpleScalaBenchmark {

  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _
  var list = List[Long]()
  override def setUp() {
    array = new Array(length)
    list = List.tabulate(length)(_.toLong)
  }

  def timeMapToSet(reps: Int): Set[Long] = repeat(reps) {
    var i = 0
    var result = Set[Long]()
    while(i < array.length) {
      result = result.map(_*2).toSet
      i = i + 1
    }
    result
  }

  def timeFoldLeft(reps: Int): Set[Long] = repeat(reps) {
    var i = 0
    var result = Set[Long]()
    while(i < array.length) {
      result = result.foldLeft(Set[Long]())(_ + _*2)
      i = i + 1
    }
    result
  }
}