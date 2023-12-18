package transformation

import org.apache.spark.sql
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.*

object NSTransformer:

  @main def nstransformer =
    val spark = SparkSession.builder
      .appName("nstransformer")
      .master(sys.env.getOrElse("SPARK_MASTER_URL", "local[*]"))
      .getOrCreate()
    import spark.implicits.*

    // runs as scheduled job every hour
    // needs configuration:
    // val conf = new SparkConf().setMaster(...).setAppName(...)
    // conf.set("spark.scheduler.mode", "FAIR")
    // val sc = new SparkContext(conf)

    // read from input parquet
    val inputDF = spark.read.parquet("/path/to/parquet/file.parquet")

    // simple transformation from parquet to parquet
    inputDF.write.partitionBy("Latitude")
      .parquet("hdfs://data.parquet.transformed")



