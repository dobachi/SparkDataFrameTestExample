package example

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by dobachi on 2016/07/11.
 */
object HiveContextSample {
  val sparkConf = new SparkConf().setAppName("SQLParallelTest")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new HiveContext(sc)
  import sqlContext._

  val data = Util.createTestData(sqlContext)
  val filtered = Util.filterData(data)
}
