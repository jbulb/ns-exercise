# Rody Cunningham, Coding Exercise


## Usage 

This exercise is written in Scala 3 as it is generally backward compatible with Scala 2 for Spark.

1. Run `./start-spark-cluster.sh` to build a Spark Docker image and
   start a 3-node Spark cluster using docker-compose.
2. Run `sbt assembly` to build the jar.
3. Run `./open-spark-uis.sh` to open Spark UIs in your browser.
4. Run `./run-ns-hello.sh` to run a hello world example (not the exercise solution). 
This script uses `spark-submit` to run `HelloWorld.scala` from a jar.
5. Run `./run-ns-producer.sh` to run pseudocode script intended to run the `NSProducer.scala` producing part of the exercise.
6. Run `./run-ns-consumer.sh` to run pseudocode script intended to run the `NSConsumer.scala` consuming part of the exercise.
7. Run `./run-ns-scheduler.sh` to run pseudocode script intended to run the `NSTransformer.scala` scheduled part of the exercise.

`run-on-cluster.sh` invokes spark-submit.

This is not a fully working solution.
Pseudo-code used where needed as indicated in the exercise instructions. 
The `./run-ns-hello.sh` script is working and invokes spark-submit to produce live results.

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

## Additional Considerations

### 1. How will you make sure only delta/new records are pulled from RAW Zone to Processed Zone?

Use fault-tolerant semantics:
https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html#fault-tolerance-semantics

### 2. How to move the old data from RAW Zone so that it does not become very large?

This may relate:
https://docs.databricks.com/en/delta/tune-file-size.html

### 3. How will you run these programs – Cluster or Client

See additional notes in `run-on-cluster.sh` regarding running in client mode vs. cluster mode, using `spark-submit`.
The default is client mode, but for performance and fault tolerance cluster mode is recommended.

### 4. Cores and Executors are needed for 1) Spark Stream Job 2) Hourly Spark Batch

From Google AI (quoted):

```
Best practices for estimating Spark application settings:
1 core per node
1 GB RAM per node
1 executor per cluster for the application manager
10 percent memory overhead per executor
Here are some other tips:
For parallelizable tasks: More available cores can speed up processing.
For jobs using more than 500 Spark cores: Set the driver core count to match the executor core count.
For receiver based streaming: You may need an extra executor slot.
For jobs with 4 topics with 3 partitions each: You need 12 executor slots to process fully in parallel.
For internal processing: Spare 1 core per machine.
If you have no information about cluster: Enable Dynamic Allocation in Spark.
In Spark 2.0+ version: Use the spark session variable to set the number of executors dynamically.
```

### 5. Avoiding small file issue
https://data-driven.com/2020/08/databricks-performance-fixing-the-small-file-problem-with-delta-lake/

## Databricks 
This solution is standalone and does not fully rely on Databricks. 

However, Databricks provides considerable features that can contribute to the solution:<br>
https://www.databricks.com/discover/data-lakes

Incorporation of Databricks would facilitate a more complete solution.







