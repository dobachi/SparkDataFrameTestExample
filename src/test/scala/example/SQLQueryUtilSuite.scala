package example

import org.apache.spark.sql.hive.test.TestHiveSingleton
import org.apache.spark.sql.{DataFrame, QueryTest, Row}
import org.scalatest.BeforeAndAfterAll
import util.TestDataUtil

/**
  * Created by dobachi on 2016/07/11.
  */
class SQLQueryUtilSuite extends QueryTest with TestHiveSingleton with BeforeAndAfterAll with TestDataUtil {

   import hiveContext.implicits._

   private var testData: DataFrame = _
   private val tableName = "mytable"

   override def beforeAll() {
     testData = hiveContext.range(1, 4).toDF("id")
     testData = hiveContext.sparkContext.parallelize(
       Array((1, "a"), (2, "a"), (3, "b"), (4, "b"))).toDF("id", "value")
     testData.registerTempTable(tableName)
   }

   override def afterAll(): Unit = {
     hiveContext.dropTempTable(tableName)
   }

   test("group") {
     checkAnswer(SQLQueryUtil.groupCountByValue(tableName, hiveContext),
       Row("a", 2) :: Row("b", 2) :: Nil)
   }

  test("parquet") {
    checkAnswer(readResourceParquetFile("people.parquet"),
      Row("Andy", 32) :: Row("John", 26) :: Nil)
  }
 }
