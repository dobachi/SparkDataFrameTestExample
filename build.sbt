// Your sbt build file. Guides on how to write one can be found at
// http://www.scala-sbt.org/0.13/docs/index.html

// Project name
name := """spark-parallel-sqltest"""

// Don't forget to set the version
version := "0.1.0-SNAPSHOT"

// All Spark Packages need a license
licenses := Seq("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))

// scala version to be used
scalaVersion := "2.11.6"
// force scalaVersion
//ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

// spark version to be used
val sparkVersion = "1.6.2"

// Needed as SBT's classloader doesn't work well with Spark
fork := true

// BUG: unfortunately, it's not supported right now
fork in console := true

// Java version
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

// add a JVM option to use when forking a JVM for 'run'
javaOptions ++= Seq("-Xmx2g", "-Dspark.master=local[2]")

// append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-unchecked")

/// Dependencies

val sparkDependencyScope = "provided"

// spark modules (should be included by spark-sql, just an example)
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % sparkDependencyScope,
  "org.apache.spark" %% "spark-core" % sparkVersion % "compile, test" classifier "tests",
  "org.apache.spark" %% "spark-sql" % sparkVersion % sparkDependencyScope,
  "org.apache.spark" %% "spark-sql" % sparkVersion % "compile, test" classifier "tests",
  "org.apache.spark" %% "spark-catalyst" % sparkVersion % sparkDependencyScope,
  "org.apache.spark" %% "spark-catalyst" % sparkVersion % "compile, test" classifier "tests",
  "org.apache.spark" %% "spark-hive" % sparkVersion % sparkDependencyScope,
  "org.apache.spark" %% "spark-hive" % sparkVersion % "compile, test" classifier "tests",
  "org.apache.spark" %% "spark-mllib" % sparkVersion % sparkDependencyScope,
  "org.apache.spark" %% "spark-streaming" % sparkVersion % sparkDependencyScope
)

// logging
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"

// testing
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"


/// Compiler plugins

// linter: static analysis for scala
// resolvers += "Linter Repository" at "https://hairyfotr.github.io/linteRepo/releases"

addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.14")


/// console

// define the statements initially evaluated when entering 'console', 'consoleQuick', or 'consoleProject'
// but still keep the console settings in the sbt-spark-package plugin

// If you want to use yarn-client for spark cluster mode, override the environment variable
// SPARK_MODE=yarn-client <cmd>
val sparkMode = sys.env.getOrElse("SPARK_MODE", "local[2]")


initialCommands in console :=
  s"""
    |import org.apache.spark.SparkConf
    |import org.apache.spark.SparkContext
    |import org.apache.spark.SparkContext._
    |
    |@transient val sc = new SparkContext(
    |  new SparkConf()
    |    .setMaster("$sparkMode")
    |    .setAppName("Console test"))
    |implicit def sparkContext = sc
    |import sc._
    |
    |@transient val sqlc = new org.apache.spark.sql.SQLContext(sc)
    |implicit def sqlContext = sqlc
    |import sqlc._
    |
    |def time[T](f: => T): T = {
    |  import System.{currentTimeMillis => now}
    |  val start = now
    |  try { f } finally { println("Elapsed: " + (now - start)/1000.0 + " s") }
    |}
    |
    |""".stripMargin

cleanupCommands in console :=
  s"""
     |sc.stop()
   """.stripMargin


/// scaladoc
scalacOptions in (Compile,doc) ++= Seq("-groups", "-implicits",
  // NOTE: remember to change the JVM path that works on your system.
  // Current setting should work for JDK7 on OSX and Linux (Ubuntu)
  "-doc-external-doc:/Library/Java/JavaVirtualMachines/jdk1.7.0_60.jdk/Contents/Home/jre/lib/rt.jar#http://docs.oracle.com/javase/7/docs/api",
  "-doc-external-doc:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/rt.jar#http://docs.oracle.com/javase/7/docs/api"
  )

autoAPIMappings := true



fork in run := true
