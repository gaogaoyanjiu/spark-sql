# spark-sql
记录spark SQL的学习笔记

1、hadoop了解（MR）、yarn资源调度的原理

2、spark SQL入门（运行模式、数据源）

3、RDD（弹性分布式数据集）、算子（懒加载的Transformation（变换/转换）、Action（动作））

  Transformation：操作是延迟计算的，也就是说从一个RDD 转换生成另一个 RDD 的转换操作不是马上执行，需要等到有 Action 操作的时候才会真正触发运算。
  Action：这类算子会触发 SparkContext 提交 Job 作业，并将数据输出 Spark系统。

4、DataFream和DataSet

5、DStream
