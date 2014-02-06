package com.danieltrinh.benchmarks

/*
 * length          benchmark      ns linear runtime
 *     10     FilterTakeView    1920 =
 *     10 FilterTakeIterator     607 =
 *     10  FilterTakeRegular    1230 =
 *    100     FilterTakeView   18214 =
 *    100 FilterTakeIterator    7619 =
 *    100  FilterTakeRegular   89342 =
 *   1000     FilterTakeView  205828 =
 *   1000 FilterTakeIterator   83449 =
 *   1000  FilterTakeRegular 8284170 ==============================
 */

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
      }.take(i/2).force

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
      }.take(i/2).toList
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
      }.take(i/2)
      i = i + 1
    }
    arguments
  }
}