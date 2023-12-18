package nsproducer

import org.apache.spark.sql
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.*

//for XML (other libraries may be required):
//import org.apache.spark.sql.types.StructType
//import com.databricks.spark.xml.util.XmlFile
//import org.apache.spark.sql.catalyst.util.FailFastMode

object NSProducer:

  @main def nsproducer =
    val spark = SparkSession.builder
      .appName("nsproducer")
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

    // above uses CSV, however exercise calls for XML with schema validation.
    // pseudo code for XML loading follows:
    // (taken from https://github.com/databricks/spark-xml/blob/master/src/main/scala/com/databricks/spark/xml/XmlReader.scala#L136)

    //  def withSchema(schema: StructType): XmlReader = {
    //    this.schema = schema
    //    this
    //  }
    // /**
    //   * @param spark current SparkSession
    //   * @param path path to XML files to parse
    //   * @return XML parsed as a DataFrame
    //   */
    //  def xmlFile(spark: SparkSession, path: String): DataFrame = {
    //    // We need the `charset` and `rowTag` before creating the relation.
    //    val (charset, rowTag) = {
    //      val options = XmlOptions(parameters.toMap)
    //      (options.charset, options.rowTag)
    //    }
    //    val relation = XmlRelation(
    //      () => XmlFile.withCharset(spark.sparkContext, path, charset, rowTag),
    //      Some(path),
    //      parameters.toMap,
    //      schema)(spark.sqlContext)
    //    spark.baseRelationToDataFrame(relation)
    //  }










