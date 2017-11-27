package com.davideanastasia

import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

package object ce {

  implicit def toCardinalityEstimator[T : ClassTag](rdd: RDD[(T, String)]) = new CardinalityEstimator(rdd)

}
