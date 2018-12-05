package com.tdtk.log

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
 * 使用Spark完成我们的数据清洗操作
 */
object SparkStatCleanJob {

  def main(args: Array[String]) {
    val spark = SparkSession.builder().appName("SparkStatCleanJob")
//      .config("spark.sql.parquet.compression.codec","gzip")  //设置压缩格式
      .master("local[2]").getOrCreate()

//    val accessRDD = spark.sparkContext.textFile("/Users/rocky/data/imooc/access.log")
    val accessRDD = spark.sparkContext.textFile("D://data/access.log")

    //accessRDD.take(10).foreach(println)

    //RDD ==> DF
    val accessDF = spark.createDataFrame(accessRDD.map(x => AccessConvertUtil.parseLog(x)),
      AccessConvertUtil.struct)

    accessDF.printSchema()
//    accessDF.show() //默认 前 20 条
    accessDF.show(false) // 不截取

//    覆盖已经存在的文件： mode(SaveMode.Overwrite)
//    控制文件输出的大小： coalesce
    accessDF.coalesce(1).write.format("parquet").mode(SaveMode.Overwrite)
//    .partitionBy("day").save("/Users/rocky/data/imooc/clean2")
      .partitionBy("day").save("D://data/clean2")

    spark.stop
  }
}
