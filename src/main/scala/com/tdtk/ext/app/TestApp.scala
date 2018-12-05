package com.tdtk.ext.app

import org.apache.spark.sql.SparkSession

/**
  * 测试自定义数据源
  * 外部数据源网址;spark-packages.org
  *
  * 未完成：自定义数据源开发
  */
object TestApp {

  def main(args: Array[String]) {
    val spark = SparkSession.builder().appName("TestApp").master("local[2]").getOrCreate()


//    val accessDF = spark.read.format("com.tdtk.ext.text").load("D://data/ext")
    val accessDF = spark.read.format("com.tdtk.ext.text").option("path","D://data/ext").load()

//    val accessDF = spark.read.format("json").load("data/people.json")

        accessDF.printSchema()
        accessDF.show(false)

    spark.close()

  }
}
