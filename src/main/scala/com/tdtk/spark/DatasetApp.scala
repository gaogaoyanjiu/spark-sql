package com.tdtk.spark

import org.apache.spark.sql.SparkSession

/**
  * Dataset操作
  *
  * 初次出现在1.6版本 有Spark SQL优化 能使用lambda表达式，但不能用python语言使用Dataset的API
  *
  * DF = DS[Row]
  * DS：强类型 typed   case class
  * DF：弱类型 Row
  *
  *
  * 读取csv文件变成DataFrame的方法：
  * val salesDF = spark.read.option("header", "true").option("inferSchema", "true”).csv(path)
  * header是指解析头文件，这样能知道列名
  * inferSchema是获取每一列的属性
  *
  * DF转DS的方法：
  * [1] 创建case class
  * [2] as方法
  */
object DatasetApp {

  def main(args: Array[String]) {

    val spark = SparkSession.builder().appName("DatasetApp")
      .master("local[2]").getOrCreate()

    //注意：需要导入隐式转换
    import spark.implicits._

    //    val path = "file:///Users/rocky/data/sales.csv"
    val path = "D://data/sales.csv"

    //spark如何解析csv文件？
    // 逗号分隔值（Comma-Separated Values，CSV，有时也称为字符分隔值，因为分隔字符也可以不是逗号），其文件以纯文本形式存储表格数据（数字和文本）
    // 通常，所有记录都有完全相同的字段序列。通常都是纯文本文件。建议使用WORDPAD或是记事本（NOTE）来开启，再则先另存新档后用EXCEL开启，也是方法之一。
    val df = spark.read.option("header", "true").option("inferSchema", "true").csv(path)
    df.show

//    as转换为DataSet
    val ds = df.as[Sales]
    ds.map(line => line.itemId).show
    ds.map(line => line).show
    ds.map(line => (1, line)).show
    ds.map(line => (line.itemId, line.customerId, line.transactionId)).show


//    演示运行之前，存在未知错误的写法：

//    spark.sql("seletc name from person").show
//
//    df.seletc("name")
//
//    df.select("nname")
//
//    ds.map(line => line.itemId)

    spark.stop()
  }

  case class Sales(transactionId: Int, customerId: Int, itemId: Int, amountPaid: Double)

}


//1 开头是不留空，以行为单位。
//2 可含或不含列名，含列名则居文件第一行。
//3 一行数据不跨行，无空行。
//4 以半角逗号（即,）作分隔符，列为空也要表达其存在。
//5列内容如存在半角引号（即"），替换成半角双引号（""）转义，即用半角引号（即""）将该字段值包含起来。
//6文件读写时引号，逗号操作规则互逆。
//7内码格式不限，可为 ASCII、Unicode 或者其他。
//8不支持数字
//9不支持特殊字符


//如果你的机器上装了 Microsoft Excel的话，.csv 文件默认是被Excel打开的。
//需要注意的是，当你双击一个.CSV 文件，Excel 打开它以后即使不做任何的修改，
//在关闭的时候 Excel 往往会提示是否要改成正确的文件格式，这个时候如果选择“是”，
//因为 Excel 认为.CSV 文件中的数字是要用科学记数法来表示的，
//Excel 会把 CSV 文件中所有的数字用科学计数来表示（2.54932E+5 这种形式），
//这样操作之后，只是在 Excel 中显示的时候会不正常，而 csv 文件由于是纯文本文件，
//在使用上没有影响；如果选择了“否”，那么会提示你以 xls 格式另存为 Excel 的一个副本。

//所以如果你的 CSV 文件绝大部分都是用在集图上的话，
//建议把.CSV 的默认打开方式改成任意一个文本 编辑器，系统自带的记事本就是个不错的选择。