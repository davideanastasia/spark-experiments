package com.davideanastasia.ce

import com.clearspring.analytics.stream.cardinality.HyperLogLog

class HyperLogLogAccumulator extends Serializable {
  private val hll = new HyperLogLog(16)

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

  def apply(d: String): HyperLogLogAccumulator = {
    val hhlAcc = new HyperLogLogAccumulator
    hhlAcc.offer(d)
    hhlAcc
  }

}