package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.concurrent.Future
import scala.util.{ Try, Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

class TryVsFutureBenchmark extends SimpleScalaBenchmark {

  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _

  override def setUp() {
    array = new Array(length)
  }


  def timeTrySuccess(reps: Int): Try[Int] = repeat(reps) {

    var i = 0
    var result = Success(i)
    while(i < array.length) {
      result = Success(i)
      i = i + 1
    }
    result
  }
  def timeTryFailure(reps: Int): Try[Int] = repeat(reps) {
    val e = new RuntimeException("Yep")
    var i = 0
    var result = Failure(e)
    while(i < array.length) {
      result = Failure(e)
      i = i + 1
    }
    result
  }

  // This 30-60 times slower than Future.successful.. leaving it out 
  // since it is so slow
  // def timeFuture(reps: Int): Future[Int] = repeat(reps) {

  //   var i = 0
  //   var result = Future(i)
  //   while(i < array.length) {
  //     result = Future(i)
  //     i = i + 1
  //   }
  //   result
  // }

  def timeFutureSuccessful(reps: Int): Future[Int] = repeat(reps) {

    var i = 0
    var result = Future.successful(i)
    while(i < array.length) {
      result = Future.successful(i)
      i = i + 1
    }
    result

  }

  def timeFutureFailed(reps: Int): Future[Int] = repeat(reps) {
    val e = new RuntimeException("Yep")

    var i = 0
    var result = Future.failed[Int](e)
    while(i < array.length) {
      result = Future.failed[Int](e)
      i = i + 1
    }
    result

  }

  def timeFutureSuccessTrySuccess(reps: Int): Future[Try[Int]] = repeat(reps) {

    var i = 0
    var result = Future.successful(Success(i))
    while(i < array.length) {
      result = Future.successful(Success(i))
      i = i + 1
    }
    result
  }

  def timeFutureSuccessTryFailure(reps: Int): Future[Try[Int]] = repeat(reps) {
    val e = new RuntimeException("Yep")

    var i = 0
    var result = Future.successful(Failure(e))
    while(i < array.length) {
      result = Future.successful(Failure(e))
      i = i + 1
    }
    result
  }


  def timeOptionSome(reps: Int): Option[Int] = repeat(reps) {

    var i = 0
    var result = Some(i)
    while(i < array.length) {
      result = Some(i)
      i = i + 1
    }
    result

  }
}