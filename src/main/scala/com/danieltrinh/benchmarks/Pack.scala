package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.collection.mutable._
import scala.annotation.tailrec

class Pack extends SimpleScalaBenchmark {
  import Pack._

  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _
  var data: List[Int] = _
  override def setUp() {
    array = new Array(length)
    data = List(1,1,1,1,2,2,2,2,2,2,2,3,4,4,4,4,4,4,5,5,5,5,6,7,3,23,2,3,3,3,3,2,1,2,2,2,2)
  }

  def timePackFoldLeft(reps: Int): List[List[Int]] = repeat(reps) {
    var i = 0
    var result = List[List[Int]]()
    while(i < array.length) {
      result = packFoldLeft(data)
      i = i + 1
    }
    result
  }

  def timePackSpanListBuffer(reps: Int): List[List[Int]] = repeat(reps) {
    var i = 0
    var result = List[List[Int]]()
    while(i < array.length) {
      result = packSpanListBuffer(data)
      i = i + 1
    }
    result
  }

  def timePackSpanRecursion(reps: Int): List[List[Int]] = repeat(reps) {
    var i = 0
    var result = List[List[Int]]()
    while(i < array.length) {
      result = packSpanRecursion(data)
      i = i + 1
    }
    result
  }
}

object Pack {
  /**
   * P09 (**) Pack consecutive duplicates of list elements into sublists.
   * If a list contains repeated elements they should be placed in separate sublists.
   * Example:
   * scala> pack(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
   * res0: List[List[Symbol]] = List(List('a, 'a, 'a, 'a), List('b), List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e))
   */
  
  def packFoldLeft[T](list: List[T]): List[List[T]] = {
    list.foldLeft(List[List[T]]()) { (resultList, elem) =>
      resultList match {
        case (innerList @ (head :: innerTail)) :: resultTail =>
          if (elem == head)
            (elem :: innerList) :: resultTail
          else
            List(elem) :: resultList
        case List(Nil) => List(List(elem))
        case Nil       => List(List(elem))
      }
    }.reverse
  }

  def packSpanRecursion[A](ls: List[A]): List[List[A]] = {
    if (ls.isEmpty) List(List())
    else {
      val (packed, next) = ls span { _ == ls.head }
      if (next == Nil) List(packed)
      else packed :: packSpanRecursion(next)
    }
  }

  def packSpanListBuffer[T](list: List[T]): List[List[T]] = {
    val packedList = ListBuffer[List[T]]()

    @tailrec
    def pack0(remainingList: List[T]): Unit = {
      remainingList match {
        case head :: tail =>
          val (a, b) = remainingList.span(_ == head)
          packedList += a
          pack0(b)
        case Nil =>
      }
    }
    pack0(list)
    packedList.toList
  }
}