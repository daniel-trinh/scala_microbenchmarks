package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param

class LazyCollectionsVsRegularCollections extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "1000", "10000"))
  val length: Int = 0

  var array: Array[Int] = _

  override def setUp() {
    array = new Array(length)
  }

  def timeRegularList(reps: Int): List[Int] = repeat(reps) {
    var i = 0
    var list = List.fill(i)(i)
    while(i < array.length) {
      i += 1
      list = list map { _ + 1 } reverse
    }
    list
  }

  def timeLazyListEarlyForce(reps: Int): List[Int] = repeat(reps) {
    var i = 0
    var list = List[Int]()
    while(i < array.length) {
      i += 1
      list = list.view.map { _ + 1}.reverse.toList
    }
    list
  }
}