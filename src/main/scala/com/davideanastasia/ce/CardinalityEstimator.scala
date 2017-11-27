package com.davideanastasia.ce

import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

class CardinalityEstimator[K : ClassTag](rdd: RDD[(K, String)]) {

  def cardinalityByKey(): RDD[(K, Long)] = rdd
    .combineByKey(v => HyperLogLogAccumulator(v) ,
      (acc: HyperLogLogAccumulator, v) => {
        acc.offer(v)
        acc
      },
      (acc1: HyperLogLogAccumulator, acc2: HyperLogLogAccumulator) => {
        acc1.merge(acc2)
        acc1
      }
    ).mapValues(_.cardinality())

}
