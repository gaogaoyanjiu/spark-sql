package com.tdtk.spark

import java.sql.DriverManager

/**
 *  通过JDBC的方式访问
  *  注意事项：在使用jdbc开发时，一定要先启动thriftserver
 */
object SparkSQLThriftServerApp {

  def main(args: Array[String]) {

    Class.forName("org.apache.hive.jdbc.HiveDriver")

    // 开发环境的hosts文件映射：192.168.199.200 hadoop001

    val conn = DriverManager.getConnection("jdbc:hive2://hadoop001:14000","hadoop","")
    val pstmt = conn.prepareStatement("select empno, ename, sal from emp")
    val rs = pstmt.executeQuery()
    while (rs.next()) {
      println("empno:" + rs.getInt("empno") +
        " , ename:" + rs.getString("ename") +
        " , sal:" + rs.getDouble("sal"))

    }

    rs.close()
    pstmt.close()
    conn.close()


  }


}
