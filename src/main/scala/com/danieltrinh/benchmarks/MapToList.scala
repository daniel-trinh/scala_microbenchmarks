package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param

class MapToList extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _
  var map = Map[Int, List[Int]]()
  override def setUp() {
    array = new Array(length)
    map = List.tabulate(length)(identity).groupBy(identity)
  }

  def timeFoldLeft(reps: Int): List[List[Int]] = repeat(reps) {
    map.foldLeft(List[List[Int]]()){ case (accu, (k, v)) => v :: accu }
  }

  def timeMapToList(reps: Int): List[List[Int]] = repeat(reps) {
    map.map(_._2).toList
  }

  def timeValuesToList(reps: Int): List[List[Int]] = repeat(reps) {
    map.values.toList
  }

  def timeValuesIteratorToList(reps: Int): List[List[Int]] = repeat(reps) {
    map.valuesIterator.toList
  }
}