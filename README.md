# Rody Cunningham, Coding Exercise

## Overview 

This exercise is written in Scala 3 as it is backward compatible with Scala 2 for Spark.

1. Run `./start-spark-cluster.sh` to build a Spark Docker image and
   start a 3-node Spark cluster using docker-compose.
2. Run `sbt assembly` to build the jar.
3. Run `./open-spark-uis.sh` to open Spark UIs in your browser.
4. Run `./run-ns-hello.sh` to run a hello world example (not the exercise solution). This script will run `HelloWorld.scala` and produce Spark output.
5. Run `./run-ns-producer.sh` to run pseudocode script intended to run the `NSProducer.scala` producing part of the exercise.
6. Run `./run-ns-consumer.sh` to run pseudocode script intended to run the `NSConsumer.scala` consuming part of the exercise.

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






