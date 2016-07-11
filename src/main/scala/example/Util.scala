package example

import org.apache.spark.sql.{DataFrame, SQLContext}

/**
 * Utilities to filter data by id
 */
object Util {
  def createData(sqlContext: SQLContext, tableName: String): DataFrame = {
    import sqlContext.implicits._

    val data = Array((1, "a"), (2, "a"), (3, "b"), (4, "b"))
    val df = sqlContext.sparkContext.parallelize(data).toDF("id", "value")
    df.registerTempTable(tableName)
    df
  }

  def filterData(data: DataFrame): DataFrame = {
    data.filter("id % 2 = 0")
  }
}
