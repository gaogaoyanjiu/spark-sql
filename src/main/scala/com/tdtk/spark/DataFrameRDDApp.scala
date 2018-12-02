package com.tdtk.spark

import org.apache.spark.sql.types.{StringType, IntegerType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
  * DataFrame和RDD的互操作
  */
object DataFrameRDDApp {

  def main(args: Array[String]) {

    val spark = SparkSession.builder().appName("DataFrameRDDApp").master("local[2]").getOrCreate()

    //inferReflection(spark)

    program(spark)

    spark.stop()
  }

  /**
    * DataFrame 与RDD 互操作方式二：如果无法提前定义样例类（列的类型事先是不知道的，只有运行的时候才知道）
    *
    * 通过StructType编程方式直接指定Schema
    *
    * spark SQL能够将含Row对象的RDD转换成DataFrame，并推断数据类型。
    * 通过将一个键值对（key/value）列表作为kwargs传给Row类来构造Rows。
    * key定义了表的列名，类型通过看第一列数据来推断。
    * （所以这里RDD的第一列数据不能有缺失）未来版本中将会通过看更多数据来推断数据类型，像现在对JSON文件的处理一样。
    *
    *
    *
    * 第二种：直接构建Dataset（动态）
    * 不知道schema的条件下使用
    * 先转成Rows，结合StructType，代码量大一点
    *
    * 生成RDD
    * 分割RDD，和第一种方法的第4步一样，然后转换成RowsRDD
    * 定义StructType，用一个数组Array来定义，每个变量的Type用StructField来定义
    * 用createDataFrame方法关联RDD和StructType
    *
    * @param spark
    */
  def program(spark: SparkSession): Unit = {
    // RDD ==> DataFrame
    // [1] 生成RDD
    val rdd = spark.sparkContext.textFile("D://data/infos.txt")
    //    val rdd = spark.sparkContext.textFile("file:///Users/rocky/data/infos.txt")

    // [2] 分割，转成rowRDD
    val infoRDD = rdd.map(_.split(",")).map(line => Row(line(0).toInt, line(1), line(2).toInt))

    // [3] 定义StructType
    val structType = StructType(Array(StructField("id", IntegerType, true),
      StructField("name", StringType, true),
      StructField("age", IntegerType, true)))

    // [4] 关联rowRDD和StructType
    val infoDF = spark.createDataFrame(infoRDD, structType)
    infoDF.printSchema()
    infoDF.show()

    //通过df的api进行操作
    infoDF.filter(infoDF.col("age") > 30).show

    //通过sql的方式进行操作
    infoDF.createOrReplaceTempView("infos")
    spark.sql("select * from infos where age > 30").show()
  }


  /**
    * DataFrame与RDD互操作方式一：反射的方式
    * 使用反射来推断包含了特定数据类型的RDD的元数据
    * 使用DataFrame API或者sql方式编程
    *
    *
    *
    * 第一种：反射
    * 代码简洁，前提是需要知道schema的构成
    * 借助case class，在这个类里定义好schema对应的字段
    *
    * 创建case class，根据schema来写
    * 生成RDD，借助SparkContext的textFile，获取文件然后转成RDD，String类型
    * 导入Spark.Implicits._  隐式转换包
    * 分割RDD，split方法，分割后变成String数组，并和case class相对应起来（也就是把对应的变量传入class中，记得传入前进行类型转换）
    * toDF方法生成DataFrame
    *
    * @param spark
    */
  def inferReflection(spark: SparkSession) {
    // RDD ==> DataFrame
    // [1] 生成RDD
    val rdd = spark.sparkContext.textFile("D://data/infos.txt")
    //    val rdd = spark.sparkContext.textFile("file:///Users/rocky/data/infos.txt")

    //注意：需要导入隐式转换
    import spark.implicits._
    //切割，分类，转换(若分隔符是|或者其他，有可能要加上转义字符\\)
    val infoDF = rdd.map(_.split(",")).map(line => Info(line(0).toInt, line(1), line(2).toInt)).toDF()

    infoDF.show()

    infoDF.filter(infoDF.col("age") > 30).show

    // 将为 dataframe 创建一张 临时表 ，以后就可以通过sql方式操作
    infoDF.createOrReplaceTempView("infos")
    spark.sql("select * from infos where age > 30").show()

  }

  // 样例类
  case class Info(id: Int, name: String, age: Int)

}
