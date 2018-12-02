package com.tdtk.spark

import org.apache.spark.sql.SparkSession

/**
  * DataFrame中的操作操作
  */
object DataFrameCase {

  def main(args: Array[String]) {
    val spark = SparkSession.builder().appName("DataFrameRDDApp").master("local[2]").getOrCreate()

    // RDD ==> DataFrame
    //    val rdd = spark.sparkContext.textFile("file:///Users/rocky/data/student.data")
    //    val rdd = spark.sparkContext.textFile("file:///home/hadoop/data/student.data")
    val rdd = spark.sparkContext.textFile("D://data/student.data")

    //注意：需要导入隐式转换
    import spark.implicits._
    val studentDF = rdd.map(_.split("\\|")).map(line => Student(line(0).toInt, line(1), line(2), line(3))).toDF()

    //show默认只显示前20条,内容长度默认截取
    studentDF.show
    studentDF.show(30)
    studentDF.show(30, false)

    //取出前10条
    studentDF.take(10).foreach(println)
    //取出第1条
    println(studentDF.first())
    studentDF.head(3).foreach(println)


    studentDF.select("email").show(30, false)


    studentDF.filter("name=''").show
    studentDF.filter("name='' OR name='NULL'").show

    // 查看spark的内置函数
    spark.sql("show functions").show(1000)
    //name以M开头的人
    studentDF.filter("SUBSTR(name,0,1)='M'").show
    studentDF.filter("substring(name,0,1)='M'").show

    studentDF.sort(studentDF("name")).show
    studentDF.sort(studentDF("name").desc).show

    studentDF.sort("name", "id").show
    studentDF.sort(studentDF("name").asc, studentDF("id").desc).show

    studentDF.select(studentDF("name").as("student_name")).show


    val studentDF2 = rdd.map(_.split("\\|")).map(line => Student(line(0).toInt, line(1), line(2), line(3))).toDF()

    //    joinType One of: `inner`, `outer`, `left_outer`, `right_outer`, `leftsemi`.
    //    判断相等时用三个=号

    studentDF.join(studentDF2, studentDF.col("id") === studentDF2.col("id")).show
    studentDF.join(studentDF2, studentDF.col("id") === studentDF2.col("id"), "inner").show
    studentDF.join(studentDF2, studentDF.col("id") === studentDF2.col("id"), "left").show
    studentDF.join(studentDF2, studentDF.col("id") === studentDF2.col("id"), "right").show
    studentDF.join(studentDF2, studentDF.col("id") === studentDF2.col("id"), "outer").show
    studentDF.join(studentDF2, studentDF.col("id") === studentDF2.col("id"), "left_outer").show
    studentDF.join(studentDF2, studentDF.col("id") === studentDF2.col("id"), "right_outer").show
    studentDF.join(studentDF2, studentDF.col("id") === studentDF2.col("id"), "leftsemi").show
    spark.stop()
  }

  case class Student(id: Int, name: String, phone: String, email: String)

}
