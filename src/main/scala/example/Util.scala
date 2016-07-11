package example

import org.apache.spark.sql.{DataFrame, SQLContext}

/**
 * Created by dobachi on 2016/07/11.
 */
object Util {
  def createTestData(sqlContext: SQLContext) = {
    sqlContext.range(0, 10).toDF("id")
  }

  def filterData(data: DataFrame) = {
    data.filter("id % 2 === 0")
  }
}
