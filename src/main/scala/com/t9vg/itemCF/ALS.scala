
package com.t9vg.itemCF

// $example on$
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.ml.recommendation.ALSModel


import org.apache.spark.sql.SparkSession


object ALS {


  case class Rating(userId: Int, movieId: Int, rating: Float, timestamp: Long)
  def parseRating(str: String): Rating = {
    val fields = str.split(",")
    assert(fields.size == 4)
    Rating(fields(0).toInt, fields(1).toInt, fields(2).toFloat, fields(3).toLong)
  }

  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("ALSExample").master("local[2]").getOrCreate()
    import spark.implicits._

    // $example on$
    val ratings = spark.read.textFile("/Users/mac/workspace/itemCF/src/main/scala/com/t9vg/itemCF/sample_itemCF.txt").map(parseRating).toDF()
    val Array(training, test) = ratings.randomSplit(Array(0.8, 0.2))

    // Build the recommendation model using ALS on the training data
    val als = new ALS().setMaxIter(5).setRegParam(0.01).setUserCol("userId").setItemCol("movieId").setRatingCol("rating")
    val model = als.fit(training)

    // Evaluate the model by computing the RMSE on the test data
    // Note we set cold start strategy to 'drop' to ensure we don't get NaN evaluation metrics
    model.setColdStartStrategy("drop")
    val predictions = model.transform(test)

    val evaluator = new RegressionEvaluator().setMetricName("rmse").setLabelCol("rating").setPredictionCol("prediction")
    val rmse = evaluator.evaluate(predictions)
    println(s"Root-mean-square error = $rmse")

    // Generate top 10 movie recommendations for each user
    val userRecs = model.recommendForAllUsers(10)
    // Generate top 10 user recommendations for each movie
    val movieRecs = model.recommendForAllItems(10)

    // Generate top 10 movie recommendations for a specified set of users
    //基于用户的系统过滤电影推荐前十
    val users = ratings.select(als.getUserCol).distinct().limit(3)
    //val userSubsetRecs = model.recommendForUserSubset(users, 10)

    // 基于物品（电影）的系统过滤推荐前十
    val movies = ratings.select(als.getItemCol).distinct().limit(3)
    //val movieSubSetRecs = model.recommendForItemSubset(movies, 10)
    // $example off$
    userRecs.show()

    movieRecs.show()
    //println("-----基于用户的系统过滤电影推荐top10------")
    //userRecs.rdd.foreach(println(_))

    //println("-----基于物品（电影）的系统过滤推荐人top10------")
    //movieRecs.rdd.foreach(println(_))
    println(rmse)

    //userSubsetRecs.show()
    //movieSubSetRecs.show()

    spark.stop()
  }
}
// scalastyle:on println

