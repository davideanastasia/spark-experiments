package com.davideanastasia.ce

import com.clearspring.analytics.stream.cardinality.HyperLogLog
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object CardinalityEstimation
{
  def main (args: Array[String]): Unit = {
    val sc = new SparkContext(
      "local[*]",
      "CardinalityCounter",
      System.getenv("SPARK_HOME")
    )

    val dataset = sc.textFile(args(0))

    val intToString = dataset
      .map(_.split("\t"))
      .map(elem => (elem(1).toInt, elem(0)))

    val cardinalityEstimation = intToString
      .combineByKey(
        v => {
          val hll = new HyperLogLog(16)
          hll.offer(v.getBytes)
          hll
        },
        (acc: HyperLogLog, v) => {
          acc.offer(v.getBytes)
          acc
        },
        (acc1: HyperLogLog, acc2: HyperLogLog) =>
        {
          acc1.addAll(acc2)
          acc1
        }
      ).map(
        elem => (elem._1, elem._2.cardinality())
      )

    cardinalityEstimation
      .map(
        elem => elem._1.toString + "\t" + elem._2.toString
      ).saveAsTextFile(args(1))
  }
}
