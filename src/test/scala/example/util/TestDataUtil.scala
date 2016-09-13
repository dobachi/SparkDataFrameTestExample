package example.util

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.test.TestHiveSingleton

/**
 * Created by dobachi on 2016/09/13.
 */
trait TestDataUtil extends TestHiveSingleton {
  protected def readResourceParquetFile(name: String): DataFrame = {
    val url = Thread.currentThread().getContextClassLoader.getResource(name)
    sqlContext.read.parquet(url.toString)
  }

}
