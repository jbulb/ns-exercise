# Norfok Southern Exercise - Scala 3, Spark Cluster

## Overview 

This exercise is written in Scala 3 as it is backward compatible with Scala 2 for Spark.

1. Run `./start-spark-cluster.sh` to build the Spark Docker image and
   start a 3-node Spark cluster using docker-compose.
2. Run `sbt assembly` to build the jar.
3. Run `./open-spark-uis.sh` to open Spark UIs in your browser.
4. Run `./run-ns-hello.sh` to run a hello world example (not the exercise solution).
5. Run `./run-ns-producer.sh` to run the producing part of the exercise (writes to Kafka topic).
6. Run `./run-ns-consumer.sh` to run the ingesting part of the exercise (reads from same Kafka topic).

See notes in run-on-cluster.sh for comments regarding running in client mode vs. cluster mode.

This is not fully working solution. 
Pseudo-code used where needed as indicated in the exercise instructions. 
All three scripts will run.

## Hello World Output

```
+--------+
|   value|
+--------+
|   hello|
| norfolk|
|southern|
|exercise|
+--------+

*** Inbound Streaming Pipeline *** - is streaming : true

*** Output Streaming Pipeline initialization starting in 5 seconds - use CTL-C to exit
```
## Producer-Phase Output

TBD

## Consumer-Phase Output

TBD






