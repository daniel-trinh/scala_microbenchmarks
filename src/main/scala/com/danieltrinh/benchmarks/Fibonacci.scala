package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param

class Fibonacci extends SimpleScalaBenchmark {
  @Param(Array( "900000"))
  val length: Int = 0

  override def setUp() {
  }

  def timeFibImpl(reps: Int): BigInt = repeat(reps) {
    FibImpl.fibonacci(BigInt(length))
  }
}

import scala.math.Ordering.BigIntOrdering
import scala.collection.mutable.ListBuffer
import Ordering.Implicits._
import Numeric.Implicits._

object FibImpl {
  /**
   * This exists so that fibonacci, factorial, max, and sum work with BigInts.
   */
  object BigIntNumeric extends Numeric[BigInt] with BigIntOrdering {
    def plus(x: BigInt, y: BigInt): BigInt  = x + y
    def minus(x: BigInt, y: BigInt): BigInt = x - y
    def times(x: BigInt, y: BigInt): BigInt = x * y
    def negate(x: BigInt): BigInt = -x
    def fromInt(x: Int): BigInt = BigInt(x)
    def toInt(x: BigInt): Int = x.toInt
    def toLong(x: BigInt): Long = x.toLong
    def toFloat(x: BigInt): Float = x.toFloat
    def toDouble(x: BigInt): Double = x.toDouble
  }

  def fibonacci[T](num: T)(implicit numeric: Numeric[T]): T = {
    import numeric._

    def fib1(m1: T = zero, m2: T = one, itersLeft: T = num): T = {
      if (itersLeft == zero) {
        m1
      } else {
        fib1(m2, m1 + m2, itersLeft - one)
      }
    }
    fib1()
  }
}