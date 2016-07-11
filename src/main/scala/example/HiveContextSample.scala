package example

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
 * An example of transform test data
 */
object HiveContextSample {
  def main (args: Array[String]){
    val sparkConf = new SparkConf().setAppName("SQLParallelTest")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new HiveContext(sc)

    val tableName = "test"

    val data = Util.createData(sqlContext, tableName)
    val filtered = Util.filterData(data)
    filtered.show

    val grouped = SQLQueryUtil.groupCountByValue(tableName, sqlContext)
    grouped.show
  }
}
