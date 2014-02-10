package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.collection.mutable._

class StringVsStringBuilder extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "1000", "10000"))
  val length: Int = 0

  var array: Array[Int] = _

  override def setUp() {
    array = new Array(length)
  }

  def timeStringBuilder(reps: Int): String = repeat(reps) {
    var i = 0
    var result = new StringBuilder()
    while(i < array.length) {
      result ++= i.toString
      i = i + 1
    }
    result.toString
  }


  def timeString(reps: Int): String = repeat(reps) {
    var i = 0
    var result = ""
    while(i < array.length) {
      result = result + i.toString()
      i = i + 1
    }
    result
  }
}