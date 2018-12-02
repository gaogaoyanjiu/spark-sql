package com.tdtk.spark

import org.apache.spark.sql.SparkSession

/**
 * Parquet文件操作
 */
object ParquetApp {

  def main(args: Array[String]) {

    val spark = SparkSession.builder().appName("SparkSessionApp")
      .master("local[2]").getOrCreate()


    val userDF = spark.read.load("D://data/output/users.parquet")
    /**
     * spark.read.format("parquet").load 这是标准写法
     */
    // 读取
//    val userDF = spark.read.format("parquet").load("file:///home/hadoop/app/spark-2.1.0-bin-2.6.0-cdh5.7.0/examples/src/main/resources/users.parquet")

    userDF.printSchema()
    userDF.show()
//
//    userDF.select("name","favorite_color").show

//    写入
    /**
      * 多次执行会报如下错误：
      * org.apache.spark.sql.AnalysisException: path file:/home/hadoop/tmp/jsonout already exists.;
      */
//    userDF.select("name","favorite_color").write.format("json").save("file:///home/hadoop/tmp/jsonout")

//    spark.read.load("file:///home/hadoop/app/spark-2.1.0-bin-2.6.0-cdh5.7.0/examples/src/main/resources/users.parquet").show

//    会报错，因为sparksql默认处理的format就是parquet
//    spark.read.load("file:///home/hadoop/app/spark-2.1.0-bin-2.6.0-cdh5.7.0/examples/src/main/resources/people.json").show
//
//    spark.read.format("parquet").option("path","file:///home/hadoop/app/spark-2.1.0-bin-2.6.0-cdh5.7.0/examples/src/main/resources/users.parquet").load().show
    spark.stop()
  }

}
