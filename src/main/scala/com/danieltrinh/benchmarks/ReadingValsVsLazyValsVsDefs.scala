package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.collection.mutable._

class ReadingValsVsLazyValsVsDefs extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "1000", "10000"))
  val length: Int = 0

  var array: Array[Int] = _

  override def setUp() {
    array = new Array(length)
  }

  val test = 1
  def calculation: Long = Long.MaxValue

  def timeVal(reps: Int): MutableObject = repeat(reps) {
    var i = 0
    val calc = calculation
    var mutated = new MutableObject()
    while(i < array.length) {
      mutated.value = calc
      i = i + 1
    }
    mutated
  }

  def timeLazyVal(reps: Int): MutableObject = repeat(reps) {
    var i = 0
    lazy val calc = calculation
    var mutated = new MutableObject()
    while(i < array.length) {
      mutated.value = calc
      i = i + 1
    }
    mutated
  }

  def timeDef(reps: Int): MutableObject = repeat(reps) {
    var i = 0
    def calc = calculation
    var mutated = new MutableObject()
    while(i < array.length) {
      mutated.value = calc
      i = i + 1
    }
    mutated
  }

  class MutableObject {
    var value: Long = 0L
  }
}
