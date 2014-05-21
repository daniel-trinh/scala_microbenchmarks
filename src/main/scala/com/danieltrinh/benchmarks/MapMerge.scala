package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scalaz.Scalaz
import Scalaz._
import scala.util.Random
import scala.collection.mutable
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.scalacheck._
import Gen._
import Arbitrary.arbitrary
import org.json4s.DefaultFormats
import helpers.Mergers._

class MapMerge extends SimpleScalaBenchmark {

  @Param(Array("10", "100", "600"))
  val length: Int = 0

  var list = List[DeepMap]()
  var viewabilityList = List[ViewabilityMap]()
  var dataTypes = Array(0.01, 3, "0.002", 2.5, 5300303L)
  def i = Random.nextInt(dataTypes.size)
  def six = List.tabulate(6)(x => (Random.nextString(10), dataTypes(i)))

  override def setUp() {
    def data = List.tabulate(length)(x => (Random.nextString(20), Map(six:_*)))
    def viewabilityData = List.tabulate(length)(_ => (Random.nextString(20), randomSitePlacementData))
    list = List.tabulate(length)(_ => Map(data:_*))
    viewabilityList = List.tabulate(length)(_ => Map(viewabilityData:_*))
  }

  def timeMapPlusPlus(reps: Int): DeepMap = repeat(reps) {
    mapPlusPlus(list)
  }

  def timeMapPlusPlusCaseClass(reps: Int): ViewabilityMap = repeat(reps) {
    mapPlusPlusCaseClass(viewabilityList)
  }

  def timeParMapFold(reps: Int): DeepMap = repeat(reps) {
    parMapFold(list)
  }

  def timeMapFold(reps: Int): DeepMap = repeat(reps) {
    mapFold(list)
  }

  def timeParMapPlusPlus(reps: Int): DeepMap = repeat(reps) {
    parMapPlusPlus(list)
  }

  // This is slow :{
//  def timeScalazSemigroup(reps: Int): DeepMap = repeat(reps) {
//     list.par.reduce(_|+|_)
//  }

  def timeMutableHashMap(reps: Int): DeepMap = repeat(reps) {
    val temp = list.foldLeft(mutable.HashMap[String, Map[String, Any]]()) { (accu, elem) =>
      for ((k, v) <- elem) {
        accu += (k -> v)
      }
      accu
    }
    temp.toMap
  }
}