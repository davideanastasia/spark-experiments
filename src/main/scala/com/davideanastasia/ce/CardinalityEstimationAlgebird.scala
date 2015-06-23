package com.davideanastasia.ce

import com.twitter.algebird.HyperLogLogMonoid
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._

object CardinalityEstimationAlgebird
{
  def main (args: Array[String]): Unit = {
    val sc = new SparkContext(
      "local[*]",
      "CardinalityCounterAlgebird",
      System.getenv("SPARK_HOME")
    )

    val dataset = sc.textFile(args(0))

    val intToString = dataset
      .map(_.split("\t"))
      .map(elem => (elem(1).toInt, elem(0)))

    // val hll = new HyperLogLogMonoid(16)
    val cardinalityEstimation = intToString
      .map(elem => (elem._1, new HyperLogLogMonoid(16).toHLL(elem._2)))
      .reduceByKey(_ + _)
      .map(
        elem => (elem._1, elem._2.estimatedSize.toInt)
      )

    cardinalityEstimation
      .map(
        elem => elem._1.toString + "\t" + elem._2.toString
      ).saveAsTextFile(args(1))
  }
}
