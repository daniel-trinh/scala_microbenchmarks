package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.collection.mutable._

class ViewZipForeach extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "1000", "10000"))
  val length: Int = 0

  var array: Array[Int] = _

  override def setUp() {
    array = new Array(length)
  }

  def timeViewZipForeach(reps: Int): Int = repeat(reps) {
    var i   = 0
    var res = 0
    while(i < array.length) {
      array.view.zipWithIndex.foreach { case (x, y) =>
        res = x
      }
      i = i + 1
    }
    res
  }

  def timeIterZipForeach(reps: Int): Int = repeat(reps) {
    var i = 0

    var res = 0
    while(i < array.length) {
      array.iterator.zipWithIndex.foreach { case (x, y) =>
        res = x
      }
      i = i + 1
    }
    res
  }

  def timeZipForeach(reps: Int): Int = repeat(reps) {
    var i = 0
    var res = 0
    while(i < array.length) {
      array.zipWithIndex.foreach { case (x, y) =>
        res = x
      }
      i = i + 1
    }
    res
  }
}