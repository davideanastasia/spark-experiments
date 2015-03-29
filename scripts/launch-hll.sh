#!/bin/bash

/opt/spark/bin/spark-submit \
    --class com.davideanastasia.ce.CardinalityEstimation \
    --master local[4] \
    target/scala-2.10/spark-experiments_2.10-1.0.jar \
    $1 $2