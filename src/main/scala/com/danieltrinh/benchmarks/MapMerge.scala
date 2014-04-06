package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scalaz.Scalaz
import Scalaz._
import scala.util.Random
import scala.collection.mutable

class MapMerge extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "600"))
  val length: Int = 0

  var array: Array[Int] = _
  var list = List[Map[String,Map[String, Int]]]()
  val six = List.tabulate(6)(x => (Random.nextString(10), x))
  override def setUp() {
    array = new Array(length)
    val data = List.tabulate(length)(x => (x.toString, Map(six:_*)))
    list = List.tabulate(length)(_ => Map(data:_*))
  }

  def timeMapPlusPlus(reps: Int): Map[String,Map[String, Int]] = repeat(reps) {
    list.reduce(_++_)
  }

  def timeParMapPlusPlus(reps: Int): Map[String,Map[String, Int]] = repeat(reps) {
    list.par.reduce(_++_)
  }

  // def timeScalazSemigroup(reps: Int): Map[String,Map[String, Int]] = repeat(reps) {
  //   list.reduce(_|+|_)
  // }

  def timeMutableHashMap(reps: Int): Map[String,Map[String, Int]] = repeat(reps) {
    val temp = list.foldLeft(mutable.HashMap[String, Map[String, Int]]()) { (accu, elem) =>
      for ((k, v) <- elem) {
        accu += (k -> v)
      }
      accu
    }
    temp.toMap
  }
}