package com.danieltrinh.benchmarks

import org.example.SimpleScalaBenchmark
import com.google.caliper.Param
import scala.collection.par._
import scala.collection.par.Scheduler.Implicits.global
import scala.collection.parallel.ParSeq

class ParallelVsRegularCollections extends SimpleScalaBenchmark {
  @Param(Array("10", "100", "1000"))
  val length: Int = 0

  var array: Array[Int] = _
  var seq = Seq[Data]()
  var parSeq = ParSeq[Data]()
  override def setUp() {
    array  = new Array(length)
    seq    = Seq.tabulate(length)(i => Data(List(i)))
    parSeq = seq.par
  }

  def timeSeqMapReduce(reps: Int): Seq[Int] = repeat(reps) {
    var i = 0
    var result = Seq[Int]()
    while(i < array.length) {
      result = seq.map(_.d).reduce { _ ++ _ }
      i = i + 1
    }
    result
  }

  def timeParSeqMapReduce(reps: Int): Seq[Int] = repeat(reps) {
    var i = 0
    var result = Seq[Int]()
    while(i < array.length) {
      result = parSeq.map(_.d).reduce(_ ++ _)
      i = i + 1
    }
    result
  }

  def timeParSeqViewMapReduce(reps: Int): Seq[Int] = repeat(reps) {
    var i = 0
    var result = Seq[Int]()
    while(i < array.length) {
      result = parSeq.view.map(_.d).reduce(_ ++ _)
      i = i + 1
    }
    result
  }

  def timeSeqFoldLeft(reps: Int): Seq[Int] = repeat(reps) {
    var i = 0
    var result = Seq[Int]()
    while(i < array.length) {
      result = seq.foldLeft(Seq[Int]()) { _ ++ _.d }
      i = i + 1
    }
    result
  }

}

case class Data(d: List[Int])