//package com.danieltrinh.benchmarks
//
//import org.example.SimpleScalaBenchmark
//import com.google.caliper.Param
//import scala.util.Random
//import org.json4s.DefaultFormats
//import helpers.Mergers._
//import org.json4s.jackson.JsonMethods._
//import org.json4s._
//import org.json4s.jackson.Serialization.{read, write}
//
//class JsonParsingAndWriting extends SimpleScalaBenchmark {
//  implicit val formats = DefaultFormats
//
//  @Param(Array("1"))
//  val l: Int = 0
//
//  val length = 600
//  var json: List[String] = List()
//
//  var viewabilityList = List[Map[String, SitePlacementData]]()
//
//  var list = List[DeepMap]()
//  var jvalue: JValue = JNothing
//
//  var dataTypes = Array(0.01, 3, "0.002", 2.5, 5300303L)
//  def i = Random.nextInt(dataTypes.size)
//  def six = List.tabulate(6)(x => (Random.nextString(10), dataTypes(i)))
//
//  override def setUp() {
//    def data = List.tabulate(length)(x => (Random.nextLong.toString, Map(six:_*)))
//    def viewabilityData = List.tabulate(length)(_ => (Random.nextLong().toString, randomSitePlacementData))
//
//    viewabilityList = List.tabulate(length)(_ => Map(viewabilityData:_*))
//    list = List.tabulate(length)(_ => Map(data:_*))
//    json = viewabilityList.map(write(x))
//  }
//
//  def timeWriteJValue(reps: Int): String = repeat(reps) {
//    parse(json)
//  }
//
//  def timeWriteViewabilityMap(reps: Int): String = repeat(reps) {
//    write(viewabilityList)
//  }
//
//  def timeWriteDeepMap(reps: Int): String = repeat(reps) {
//    write(list)
//  }
//}