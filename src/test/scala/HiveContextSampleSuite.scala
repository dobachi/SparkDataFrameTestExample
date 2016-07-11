import org.apache.spark.sql.hive.test.TestHiveSingleton
import org.apache.spark.sql.QueryTest

/**
 * Created by dobachi on 2016/07/11.
 */
class HiveContextSampleSuite extends QueryTest with TestHiveSingleton {
  test("table name with schema") {
    // regression test for SPARK-11778
    hiveContext.sql("create schema usrdb")
    hiveContext.sql("create table usrdb.test(c int)")
    hiveContext.read.table("usrdb.test")
    hiveContext.sql("drop table usrdb.test")
    hiveContext.sql("drop schema usrdb")
  }
}
