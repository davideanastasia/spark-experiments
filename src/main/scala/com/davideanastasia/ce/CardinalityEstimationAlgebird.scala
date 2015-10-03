package com.davideanastasia.ce

import com.twitter.algebird.HyperLogLogMonoid
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._

object CardinalityEstimationAlgebird {

  def main (args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("CardinalityCounterAlgebird")
    val sc = new SparkContext(conf)

    val dataset = sc.textFile(args(0))

    val intToString = dataset
      .map(elem => {
        val tokens = elem.split("\t")

        (tokens(1).toInt, tokens(0))
      })

    val hll = new HyperLogLogMonoid(bits = 12)
    val cardinalityEstimation = intToString
      .map { case (k, v) => (k, hll.toHLL(v)) }
      .reduceByKey(hll.plus)

    cardinalityEstimation
      .repartition(1)
      .map {
        case (k, v) => Array(k, v.estimatedSize.toInt).mkString("\t")
      }
      .saveAsTextFile(args(1))
  }
}
