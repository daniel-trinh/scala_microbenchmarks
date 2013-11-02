package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.concurrent.Future
import scala.util.{ Try, Failure, Success}
import scala.util.control.NoStackTrace
import scala.concurrent.ExecutionContext.Implicits.global

class ExceptionHandlingBenchmark extends SimpleScalaBenchmark {

  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _


  override def setUp() {
    array = new Array(length)
  }

  def timeTryFailure(reps: Int): Try[Int] = repeat(reps) {
    var i = 0
    var result = Failure(new RuntimeException(s"$i"))
    while(i < array.length) {
      result = Failure(new RuntimeException(s"$i"))
      i = i + 1
    }
    result

  }

  def timeTryFailureNoStackTrace(reps: Int): Try[Int] = repeat(reps) {
    var i = 0
    var result = Failure(new RuntimeException(s"$i") with NoStackTrace)
    while(i < array.length) {
      result = Failure(new RuntimeException(s"$i") with NoStackTrace)
      i = i + 1
    }
    result

  }

  def timeFutureFailed(reps: Int): Future[Int] = repeat(reps) {
    var i = 0
    var result = Future.failed[Int](new RuntimeException(s"$i"))
    while(i < array.length) {
      result = Future.failed[Int](new RuntimeException(s"$i"))
      i = i + 1
    }
    result

  }


  def timeFutureFailedNoStackTrace(reps: Int): Future[Int] = repeat(reps) {
    var i = 0
    var result = Future.failed[Int](new RuntimeException(s"$i") with NoStackTrace)
    while(i < array.length) {
      result = Future.failed[Int](new RuntimeException(s"$i") with NoStackTrace)
      i = i + 1
    }
    result

  }

  def timeTryCatch(reps: Int): Throwable = repeat(reps) {
    var i = 0

    var result = try { 
      throw new RuntimeException(s"$i")        
    } catch {
      case e: RuntimeException => e 
    }

    while(i < array.length) {
      try { 
        throw new RuntimeException(s"$i")        
      } catch {
        case e: RuntimeException => result = e 
      }
      i = i + 1
    }
    result
  }


  def timeTryCatchNoStackTrace(reps: Int): Throwable = repeat(reps) {
    var i = 0

    var result = try { 
      throw new RuntimeException(s"$i") with NoStackTrace        
    } catch {
      case e: RuntimeException => e 
    }

    while(i < array.length) {
      try { 
        throw new RuntimeException(s"$i") with NoStackTrace        
      } catch {
        case e: RuntimeException => result = e 
      }
      i = i + 1
    }
    result
  }
}