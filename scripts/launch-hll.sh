#!/bin/bash

/opt/spark/bin/spark-submit \
    --class com.davideanastasia.ce.CardinalityEstimation \
    --master local[*] \
    target/scala-2.10/spark-experiments-assembly-1.0.jar \
    $1 $2
