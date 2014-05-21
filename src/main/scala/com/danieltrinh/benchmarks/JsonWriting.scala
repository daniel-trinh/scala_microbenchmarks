package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.util.Random
import org.json4s.DefaultFormats
import helpers.Mergers._
import org.json4s.jackson.JsonMethods._
import org.json4s._
import org.json4s.jackson.Serialization.{read, write}
import scalax.io.Resource

class JsonWriting extends SimpleScalaBenchmark {
  implicit val formats = DefaultFormats

  @Param(Array("1"))
  val l: Int = 0

  val length = 600
  var json: String = ""

  var viewabilityList = List[Map[String, SitePlacementData]]()

  var list = List[DeepMap]()
  var jvalue: JValue = JNothing

  var dataTypes = Array(0.01, 3, "0.002", 2.5, 5300303L)
  def i = Random.nextInt(dataTypes.size)
  def six = List.tabulate(6)(x => (Random.nextString(10), dataTypes(i)))

  override def setUp() {
    def data = List.tabulate(length)(x => (Random.nextLong.toString, Map(six:_*)))
    def viewabilityData = List.tabulate(length)(_ => (Random.nextLong().toString, randomSitePlacementData))

    viewabilityList = List.tabulate(length)(_ => Map(viewabilityData:_*))
    list = List.tabulate(length)(_ => Map(data:_*))

    json = write(viewabilityList)
    jvalue = parse(json)

    val output = Resource.fromFile("out.json")
    output.write("hi\n")
  }

  def timeWriteJValue(reps: Int): String = repeat(reps) {
    write(jvalue)
  }

  def timeWriteViewabilityMap(reps: Int): String = repeat(reps) {
    write(viewabilityList)
  }

  def timeWriteDeepMap(reps: Int): String = repeat(reps) {
    write(list)
  }
}