package com.tdtk.spark

import org.apache.spark.sql.SparkSession

/**
  * DataFrame API基本操作
  *
  * DataFrame对比RDD
  *
  * DataFrame有具体的列信息
  *
  * 运行效率上：
  * RDD：java/scala => jvm
  * Python 自己的运行环境
  * DataFrame：无论哪种语言都是同一个logic plan
  *
  * DataFrame 的 API：
  *
  * printschema() 输出一个树形结构
  * show() 输出内容。括号内可限制输出的条数
  * Select(COLUMN_NAME) 查询某一列所有的数据
  * 综合应用：
  * peopleDF.select(peopleDF.col("name"), (peopleDF.col("age") + 5).as("age after 5 years")).show()
  * 查找两列，并对其中一列进行运算后，更改其列名
  *
  * 过滤：
  * filter()
  * peopleDF.filter(peopleDF.col("age") > 24).show()
  *
  * 分组：
  * groupBy()
  * peopleDF.groupBy("age").count().show()
  *
  * 转成临时视图（进行SQL操作）：
  * createOrReplaceTempView()  即可转成sql API进行操作
  */
object DataFrameApp {

  def main(args: Array[String]) {

    val spark = SparkSession.builder().appName("DataFrameApp").master("local[2]").getOrCreate()

    // 将json文件加载成一个dataframe
    val peopleDF = spark.read.format("json").load("D://data/people.json")
    //    val peopleDF = spark.read.format("json").load("file:///Users/rocky/data/people.json")

    // 输出dataframe对应的schema信息
    peopleDF.printSchema()

    // 默认输出数据集的前20条记录
    peopleDF.show()

    //查询某列所有的数据： select name from table
    peopleDF.select("name").show()

    // 查询某几列所有的数据，并对列进行计算： select name, age+10 as age2 from table
    peopleDF.select(peopleDF.col("name"), (peopleDF.col("age") + 10).as("age2")).show()

    //根据某一列的值进行过滤： select * from table where age>19
    peopleDF.filter(peopleDF.col("age") > 19).show()

    //根据某一列进行分组，然后再进行聚合操作： select age,count(1) from table group by age
    peopleDF.groupBy("age").count().show()

    //根据 where 条件查询：select age,count(1) from table where age=24 and name='lisi' group by name,age
    peopleDF.select("name", "age").where("age=24 and name='lisi'").groupBy("name", "age").count().show()

    //根据 where 条件查询：select name,age from table where age is not null
    peopleDF.select("name", "age").where("age is not null").show()

    spark.stop()
  }

}
