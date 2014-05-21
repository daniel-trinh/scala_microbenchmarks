package com.danieltrinh.benchmarks.helpers

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

object Mergers {
  implicit val formats = DefaultFormats

  def genViewabilityData[T <: ViewabilityData](builder: (Long, Long, Float, Long, Long, Float) => T) = for {
    id <- arbitrary[Long]
    mImp <- arbitrary[Long]
    mPercentage <- arbitrary[Float]
    oImp <- arbitrary[Long]
    vImp <- arbitrary[Long]
    vPercentage <- arbitrary[Float]
  } yield builder(id, mImp, mPercentage, oImp, vImp, vPercentage)

  def genSitePlacementData = genViewabilityData(SitePlacementData.apply)

  def randomSitePlacementData = genSitePlacementData.sample.get

  trait ViewabilityData {
    def id: Long
    def measurable_impressions: Long
    def measurable_percentage: Float
    def overall_impressions: Long
    def viewable_impressions: Long
    def viewable_percentage: Float
  }

  case class SitePlacementData(
    site_placement_id: Long,
    measurable_impressions: Long,
    measurable_percentage: Float,
    overall_impressions: Long,
    viewable_impressions: Long,
    viewable_percentage: Float
  ) extends ViewabilityData {
    val id = site_placement_id
  }

  type DeepMap = Map[String, Map[String, Any]]
  type ViewabilityMap = Map[String, ViewabilityData]

  def mapPlusPlus(list: List[DeepMap]): DeepMap = list.reduce(_++_)
  def mapPlusPlusCaseClass(list: List[ViewabilityMap]): ViewabilityMap = list.reduce(_++_)
  def parMapPlusPlus(list: List[DeepMap]): DeepMap = list.par.reduce(_++_)
  def parMapFold(list: List[DeepMap]): DeepMap = {
    list.par.reduce { (accu, elem) =>
      elem.foldLeft(accu) { case (a, (k, v)) =>
        a + (k -> v)
      }
    }
  }

  def mapFold(list: List[DeepMap]): DeepMap = {
    list.reduce { (accu, elem) =>
      elem.foldLeft(accu) { case (a, (k, v)) =>
        a + (k -> v)
      }
    }
  }

  def mutableHashMap(list: List[DeepMap]): DeepMap = {
    val temp = list.foldLeft(mutable.HashMap[String, Map[String, Any]]()) { (accu, elem) =>
      for ((k, v) <- elem) {
        accu += (k -> v)
      }
      accu
    }
    temp.toMap
  }
}