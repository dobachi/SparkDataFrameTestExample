# Example of testing DataFrame API and SQL queries

## Highlights

- Helper method creates test data
- Separated utilities to calculate contents of DataFrame
- Separated tests for each utility

## Requirement

- SBT or Activator are installed.
  I use activator command in this example.

## Libraries included

- Spark 1.6.2
- Scala 2.11.6
- ScalaTest 2.2.4
- ScalaCheck 1.12.2

## Getting started

### Run application

```shell
$ activator run
```

### Test application

```shell
$ activator test
```

### Generate Uber jar

This example includes configuration of sbt-assembly,
so you can run 'assembly'

```shell
$ activator assembly
```

## Static Analyzer

The following static analyzers are included in build.sbt

### linter (https://github.com/HairyFotr/linter)

Usage: automatically runs during Compilation and evaluation in console

### sbt-scapegoat (https://github.com/sksamuel/sbt-scapegoat)

Usage: automatically runs during Compilation

Open target/scala-2.11/scapegoat.xml or target/scala-2.11/scapegoat.html

## Coding Style Checker

### ScalaStyle

Usage: ```sbt scalastyle```

Open target/scalastyle-result.xml

Check level are all "warn", change to "error" if you want to reject code changes when integrated with CI tools.

## Issues

### fork limitation in sbt console

This issue is originally mentioned in [Sample Project for Spark 1.3.0 with Scala 2.11.6](http://www.lightbend.com/activator/template/spark-sample-project),
a sample Spark application template.

Currently Test and run will fork a JVM. The reason it's necessary is that SBT's classloader doesn't work well with Spark and Spark shell.

However `sbt console` does not recognize fork key right now. It might throw ScalaReflectionException, etc.

## Author

- dobachi (dobachi1983oss@gmail.com)

### Application template

- Jianshi Huang (jianshuang@paypal.com; jianshi.huang@gmail.com)
