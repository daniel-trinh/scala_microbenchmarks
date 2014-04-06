package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param

class MapIsEmpty extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _
  var map = Map[Int, List[Int]]()
  override def setUp() {
    array = new Array(length)
    map = List.tabulate(length)(identity).groupBy(identity)
  }

  def timeMapIsEmpty(reps: Int): Option[Boolean] = repeat(reps) {
    var i = 0
    var isEmpty = false
    while(i < array.length) {
      isEmpty = map.isEmpty
      i += 1
    }
    Some(isEmpty)
  }

  def timeMapIterIsEmpty(reps: Int): Option[Boolean] = repeat(reps) {
    var i = 0
    var isEmpty = false
    while(i < array.length) {
      isEmpty = map.keysIterator.isEmpty
      i += 1
    }
    Some(isEmpty)
  }
}