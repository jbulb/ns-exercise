package nsproducer

import org.apache.spark.sql
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.*

object NSProducer:

  @main def produce =
    val spark = SparkSession.builder
      .appName("nsproducer")
      .master(sys.env.getOrElse("SPARK_MASTER_URL", "local[*]"))
      .getOrCreate()
    import spark.implicits.*

    val df = List("hello", "norfolk", "southern").toDF
    df.show()

    runPipelines(spark)

  def runPipelines(spark: SparkSession) =

    val inboundDF = (spark
      .readStream
      .format("rate")
      .option("rowsPerSecond", 1)
      .load()
      )

    println(s"\n\n*** Inbound Streaming Pipeline *** - is streaming : ${inboundDF.isStreaming}\n\n")
    println(s"\n\n*** Output Streaming Pipeline initialization staring in 5 seconds - use CTL-C to exit\n\n")
    Thread.sleep(5000)

    val outboundDF = inboundDF
      .withColumn("result", col("value") + lit(1))

    outboundDF
      .writeStream
      .outputMode("append")
      .option("truncate", false)
      .format("console")
      .start()
      .awaitTermination()





