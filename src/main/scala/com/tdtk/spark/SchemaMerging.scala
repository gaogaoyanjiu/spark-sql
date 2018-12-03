package com.tdtk.spark

import org.apache.spark.sql.SparkSession

/**
  * 演示Schema合并:
  * 将不同的目录下的文件内容合并为一个大而全的schema
  * 消耗很大的运行开销：默认是关闭状态
  *
  * key=1 ...key=n 表示分区 ：key 不变 ，value累加(value可以是字符串，但是不能相同)
  *
  * 可以写成：
  * key=1 ...key=n 或者 a=1 ... a=n
  */
object SchemaMerging {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("SchemaMerging").master("local[2]").getOrCreate()

    import spark.implicits._

    // [1] 模拟第一个文件
    val squaresDF = spark.sparkContext.makeRDD(1 to 5).map(i => (i, i * i)).toDF("value", "square")
    squaresDF.show()
    //    squaresDF.write.parquet("D://data/test_table/key=1")
    squaresDF.write.parquet("D://data/test_table/a=1")
    // [2] 模拟第二个文件
//    val cubesDF = spark.sparkContext.makeRDD(6 to 10).map(i => (i, i * i * i)).toDF("value", "cube")
    val cubesDF = spark.sparkContext.makeRDD(6 to 10).map(i => (i, i * i * i)).toDF("value2", "cube")
    cubesDF.show()
    //  cubesDF.write.parquet("D://data/test_table/key=2")
    cubesDF.write.parquet("D://data/test_table/a=2")

    // [3] 开启 mergeSchema，读取所有文件的共同的上一层父目录
    val mergedDF = spark.read.option("mergeSchema", "true").parquet("D://data/test_table")
    mergedDF.show()
    mergedDF.printSchema()

    // [4] 输出到另外的一个文件
    mergedDF.write.parquet("D://data/test_tmp")
    spark.read.parquet("D://data/test_tmp").show()

  }

}
