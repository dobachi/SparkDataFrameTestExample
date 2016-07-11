package example

import org.apache.spark.sql.{SQLContext, DataFrame}

/**
 * Utility to calculate the number of grouped values
 */
object SQLQueryUtil {
  def groupCountByValue(tableName: String, sqlContext: SQLContext): DataFrame = {
    sqlContext.sql("SELECT value, count(1) AS count FROM " + tableName + " GROUP BY value")
  }

}
