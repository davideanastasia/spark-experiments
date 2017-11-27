#!/bin/bash

DATA=$1
ID=$2

awk -F '\t' '{ if ($2 == "'$ID'") print $1 }' $DATA | sort | uniq | wc -l