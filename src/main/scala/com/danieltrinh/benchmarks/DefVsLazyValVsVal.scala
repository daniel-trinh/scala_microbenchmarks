package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param

class DefVsLazyValVsVal extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "1000", "10000"))
  val length: Int = 0

  var array: Array[Int] = _

  override def setUp() {
    array = new Array(length)
  }

  def timeTryVal(reps: Int): MutableObject = repeat(reps) {
    var i = 0
    var result = new MutableObject
    while(i < array.length) {
      val b = setString(result, i+i)
      result = b
      i = i + 1
    }
    result
  }


  def timeTryLazyVal(reps: Int): MutableObject = repeat(reps) {
    var i = 0
    var result = new MutableObject
    while(i < array.length) {
      lazy val b = setString(result, i+i)
      result = b
      i = i + 1
    }
    result
  }

  def timeTryDef(reps: Int): MutableObject = repeat(reps) {
    var i = 0
    var result = new MutableObject
    while(i < array.length) {
      def b = setString(result, i+i)
      result = b
      i = i + 1
    }
    result
  }

  /**
   * Prevents compiler from optimizing vals away
   */
  def setString(changeMe: MutableObject, to: Int): MutableObject = {
    changeMe.value = to
    changeMe
  }
}

class MutableObject {
  var value: Int = 0
}