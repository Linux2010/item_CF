package com.t9vg.itemCF

import org.apache.spark.sql.SparkSession

/**
  * @author tianfusheng
  * @e-mail linuxmorebetter@gmail.com
  * @date 2020/1/11
  */
object FormatALS {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("FormatALS").master("local[*]").getOrCreate()
    val sc =  spark.sparkContext
    val ratings_logs = spark.read.csv("/data/ratings")
    val ratings = ratings_logs.drop("id")
    ratings.rdd.saveAsTextFile("/ratings")

  }

}
