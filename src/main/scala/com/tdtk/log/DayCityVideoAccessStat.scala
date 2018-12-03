package com.tdtk.log

/**
  * 每天按照城市的课程访问次数实体类
  */
case class DayCityVideoAccessStat(day:String, cmsId:Long, city:String,times:Long,timesRank:Int)
