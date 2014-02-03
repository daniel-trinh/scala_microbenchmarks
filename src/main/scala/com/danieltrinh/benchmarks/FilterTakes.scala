package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import PartialFunction._

class FilterTakes extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _
  var list: List[Int] = _
  override def setUp() {
    array = new Array(length)
    list = List.tabulate(length)(x => x)
  }

  def timeFilterTakeView(reps: Int): List[Int] = repeat(reps) {
    var i = 0
    
    var arguments: List[Int] = Nil

    while(i < array.length) {
      arguments = list.view.filter { x =>
        cond(x) {
          case x: Int => true
        }
      }.take(2).toList

      i = i + 1
    }

    arguments
  }

  def timeFilterTakeIterator(reps: Int): List[Int] = repeat(reps) {
    var i = 0

    var arguments: List[Int] = Nil
    while(i < array.length) {
      arguments = list.iterator.filter { x =>
        cond(x) {
          case x: Int => true
        }
      }.take(2).toList
      i = i + 1
    }

    arguments
  }

  def timeFilterTakeRegular(reps: Int): List[Int] = repeat(reps) {
    var i = 0
    
    var arguments: List[Int] = Nil
    while(i < array.length) {
      arguments = list.filter { x =>
        cond(x) {
          case x: Int => true
        }
      }.take(2)
      i = i + 1
    }
    arguments
  }
}