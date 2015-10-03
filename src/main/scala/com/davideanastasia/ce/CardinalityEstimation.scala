package com.davideanastasia.ce

import com.clearspring.analytics.stream.cardinality.HyperLogLog
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._

object CardinalityEstimation  {

  class HyperLogLogAccumulator extends Serializable {
    val hll = new HyperLogLog(16)

    def offer(s: String): Unit = {
      hll.offer(s.getBytes)
    }

    def cardinality(): Long = {
      hll.cardinality()
    }

    def merge(other: HyperLogLogAccumulator) = {
      hll.addAll(other.hll)
    }
  }

  object HyperLogLogAccumulator {
    def apply(d : String): HyperLogLogAccumulator = {
      val hhlAcc = new HyperLogLogAccumulator
      hhlAcc.offer(d)
      hhlAcc
    }
  }


  def main (args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("CardinalityCounter")
    val sc = new SparkContext(conf)

    val dataset = sc.textFile(args(0))

    val intToString = dataset
      .map(elem => {
        val tokens = elem.split("\t")

        (tokens(1).toInt, tokens(0))
      })

    val cardinalityEstimation = intToString
      .combineByKey(
        v => {
          HyperLogLogAccumulator(v)
        },
        (acc: HyperLogLogAccumulator, v) => {
          acc.offer(v)
          acc
        },
        (acc1: HyperLogLogAccumulator, acc2: HyperLogLogAccumulator) => {
          acc1.merge(acc2)
          acc1
        }
      )

    cardinalityEstimation
      .repartition(1)
      .map {
        case (k, v) => Array(k, v.cardinality()).mkString("\t")
      }
      .saveAsTextFile(args(1))
  }
}
