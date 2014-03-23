package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.concurrent.Future
import scala.util.{ Try, Failure, Success}
import scala.util.control.NoStackTrace
import java.util.UUID

class UUIDVsCounterVsTimestampBenchmark extends SimpleScalaBenchmark {

  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _

  override def setUp() {
    array = new Array(length)
  }

  def timeUUID(reps: Int): String = repeat(reps) {
    var i = 0
    var uuid: String = UUID.randomUUID.toString
    while(i < array.length) {
      val prevUUID = uuid
      uuid = UUID.randomUUID.toString
      if (prevUUID != uuid) {
        i = i + 1
      }
    }
    uuid
  }

  def timeCounter(reps: Int): Long = repeat(reps) {
    var i = 0

    var counter: Long = 0L
    while(i < array.length) {
      val prevCounter = counter
      counter = counter + 1L
      if (prevCounter < counter) {
        i = i + 1
      }
    }
    counter
  }

  def timeTimestamp(reps: Int): Long = repeat(reps) {
    var i = 0

    var timestamp: Long = System.currentTimeMillis
    while(i < array.length) {
      val prevTimestamp = timestamp
      timestamp = System.currentTimeMillis
      if (prevTimestamp < timestamp) {
        i = i + 1
      }
    }
    timestamp
  }
}