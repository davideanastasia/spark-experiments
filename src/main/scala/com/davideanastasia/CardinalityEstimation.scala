package com.davideanastasia

import org.apache.spark.{SparkConf, SparkContext}

import com.davideanastasia.ce._

object CardinalityEstimation {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("CardinalityEstimation")
    val sc = new SparkContext(conf)

    val dataset = sc.textFile(args(0))
    val intToString = dataset
      .map(elem => {
        val tokens = elem.split("\t")

        (tokens(1).toInt, tokens(0))
      })

    val cardinalityEstimation = intToString
      .cardinalityByKey()

    cardinalityEstimation
      .repartition(1)
      .map {
        case (k, v) => Seq(k, v).mkString("\t")
      }
      .saveAsTextFile(args(1))
  }
}
