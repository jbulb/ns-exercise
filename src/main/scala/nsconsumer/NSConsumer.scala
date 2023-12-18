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

    // subscribe to topic
    // per Spark documentation for fault-tolerant semantics:
    // https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html#fault-tolerance-semantics
    // to manage error handling, continuous streaming, and checkpoint restarts from an specific offset
    val consumerDF = spark
      .read
      .format("kafka")
      .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
      .option("subscribe", "ns-exercise")
      .format("kafka")
      .format("com.databricks.spark.xml")
      .load()

    // output to Parquet with partitioning by Date
    // see also https://spark.apache.org/docs/latest/sql-data-sources-parquet.html#configuration
    consumerDF.write.partitionBy("Date", "key").parquet("hdfs://data.parquet")



