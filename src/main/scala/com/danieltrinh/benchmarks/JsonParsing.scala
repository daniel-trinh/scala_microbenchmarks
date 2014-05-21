package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.util.Random
import org.json4s.DefaultFormats
import helpers.Mergers._
import org.json4s.jackson.JsonMethods._
import org.json4s._
import org.json4s.jackson.Serialization.{read, write}

class JsonParsing extends SimpleScalaBenchmark {
  implicit val formats = DefaultFormats

  @Param(Array("1"))
  val l: Int = 0
  val length = 600
  var json: String = ""
  override def setUp() {

    def viewabilityData = List.tabulate(length)(_ => (Random.nextLong().toString, randomSitePlacementData))
    val viewabilityList = List.tabulate(length)(_ => Map(viewabilityData:_*))
    json = write(viewabilityList)
  }

  def timeParseJValue(reps: Int): JValue = repeat(reps) {
    parse(json)
  }

  def timeParseExtractViewabilityMap(reps: Int): List[Map[String, SitePlacementData]] = repeat(reps) {
    parse(json).extract[List[Map[String, SitePlacementData]]]
  }
}