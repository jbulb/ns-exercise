package helloworld

import org.apache.spark.*


object Hello {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("MinTemperatures")
      .master("local[*]")
      .getOrCreate()

    val numLines = 12345

    println("Hello world! The u.data file has " + numLines + " lines.")

    spark.stop()
  }
}
