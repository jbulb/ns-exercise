scalaVersion := "3.1.1"

libraryDependencies ++= Seq(
  ("org.apache.spark" %% "spark-sql" % "3.2.0" % "provided").cross(CrossVersion.for3Use2_13),
  ("com.github.pureconfig" %% "pureconfig" % "0.17.1").cross(CrossVersion.for3Use2_13),
  ("io.github.vincenzobaz" %% "spark-scala3" % "0.1.3").exclude("org.apache.spark", "spark-sql_2.13")
)

// include the 'provided' Spark dependency on the classpath for `sbt run`
Compile / run := Defaults.runTask(Compile / fullClasspath, Compile / run / mainClass, Compile / run / runner).evaluated

import sbt._

val Hello = config("helloworld") extend (Compile)
val Producer = config("nsproducer") extend (Compile)

def withPackage(name: String, main: String): Seq[Def.Setting[_]] =
  baseAssemblySettings ++ inTask(assembly)(
    Seq(
      mainClass       := Some(main),
      assemblyJarName := s"$name.jar"
    )
  )

