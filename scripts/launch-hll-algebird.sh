#!/bin/bash

/opt/spark/bin/spark-submit \
    --class com.davideanastasia.ce.CardinalityEstimationAlgebird \
    --master local[4] \
    target/scala-2.10/spark-experiments-assembly-1.0.jar \
    $1 $2
