package example

import org.apache.spark.sql.hive.test.TestHiveSingleton
import org.apache.spark.sql.{Row, DataFrame, QueryTest}
import org.scalatest.BeforeAndAfterAll

/**
 * Created by dobachi on 2016/07/11.
 */
class UtilSuite extends QueryTest with TestHiveSingleton with BeforeAndAfterAll{

  import hiveContext.implicits._

  private var testData: DataFrame = _

  override def beforeAll() {
    testData = hiveContext.range(1, 4).toDF("id")
    testData = hiveContext.sparkContext.parallelize(
      Array((1, "a"), (2, "a"), (3, "b"), (4, "b"))).toDF("id", "value")
    testData.registerTempTable("mytable")
  }

  override def afterAll(): Unit = {
    hiveContext.dropTempTable("mytable")
  }

  test("filter") {
    checkAnswer(Util.filterData(testData), Row(2, "a") :: Row(4, "b") :: Nil)
  }
}
