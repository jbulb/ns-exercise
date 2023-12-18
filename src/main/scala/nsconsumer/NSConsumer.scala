package nsconsumer

import org.apache.spark.sql
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.*

object NSConsumer:

  @main def nsconsumer =
    val spark = SparkSession.builder
      .appName("nsconsumer")
      .master(sys.env.getOrElse("SPARK_MASTER_URL", "local[*]"))
      .getOrCreate()
    import spark.implicits.*

    // read raw data
    val rawDataDf = spark.read.option("header", true).csv("locomotives.csv")

    // set up streaming dataframe
    val producerDf = rawDataDf.
      withColumn("value", concat_ws("|", $"LocomotiveId", $"Latitude", $"Longitude"))

    // stream result to Kafka
    producerDf
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("topic", "ns-exercise")
      .option("checkpointLocation", "checkpoint/kafka_checkpoint")
      .start()
      .awaitTermination()










