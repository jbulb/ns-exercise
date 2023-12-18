package nsproducer

import org.apache.spark.sql
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.*
import org.apache.spark.sql.types.StructType
import com.databricks.spark.xml.util.XmlFile
import org.apache.spark.sql.catalyst.util.FailFastMode

object NSProducer:

  def withSchema(schema: StructType): XmlReader = {
    this.schema = schema
    this
  }

  @main def nsproducer =
    val spark = SparkSession.builder
      .appName("nsproducer")
      .master(sys.env.getOrElse("SPARK_MASTER_URL", "local[*]"))
      .getOrCreate()
    import spark.implicits.*

    // read raw data
    // CSV example: val rawDataDf = spark.read.option("header", true).csv("locomotives.csv")
    val rawDataDF = spark.read.textfile("locomotives.xml")




    // set up streaming dataframe
    val producerDF= rawDataDF.
      withColumn("value", concat_ws("|", $"LocomotiveId", $"Latitude", $"Longitude"))

    // stream result to Kafka
    producerDF
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("topic", "ns-exercise")
      .option("checkpointLocation", "checkpoint/kafka_checkpoint")
      .start()
      .awaitTermination()










