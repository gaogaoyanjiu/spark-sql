package com.tdtk.spark

import org.apache.spark.sql.SparkSession

/**
 * SparkSession的使用
 */
object SparkSessionApp {

  def main(args: Array[String]) {

    val path = args(0)

    val spark = SparkSession.builder().appName("SparkSessionApp")
      .master("local[2]").getOrCreate()

//    val people = spark.read.format("json").load(path)
    val people = spark.read.json(path)
//    val people = spark.read.json("D://data/people.json")
//    val people = spark.read.json("file:///Users/rocky/data/people.json")
    people.show()

    spark.stop()
  }
}
