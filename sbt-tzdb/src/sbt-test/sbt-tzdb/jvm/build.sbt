name := "tzdb"

enablePlugins(TzdbPlugin)
enablePlugins(ScalaJSPlugin)

scalaVersion := "2.13.18"

crossScalaVersions := Seq("2.13.18", "2.12.21", "3.3.6")

tzdbPlatform := TzdbPlugin.Platform.Jvm

dbVersion := TzdbPlugin.Version("2019c")

// The generated provider must parse in a codebase that has turned significant
// indentation OFF. Brace syntax is valid either way, indentation-based syntax is not,
// so compiling the Scala 3 output under -no-indent is what keeps the scala-3 resource
// usable by both kinds of build.
scalacOptions ++= (if (scalaBinaryVersion.value == "3") Seq("-no-indent") else Seq.empty)

// doesn't work to do this `inThisBuild`
lazy val commonSettings = Seq(
  Compile / doc / scalacOptions --= Seq(
    "-Xfatal-warnings",
    "-deprecation"
  )
)

libraryDependencies ++= {
  val sbt2    = sbtVersion.value.startsWith("2.")
  val reflect = if (sbt2) "portable-scala-reflect" else "portable-scala-reflect_sjs1"
  val sjt     = if (sbt2) "scala-java-time" else "scala-java-time_sjs1"
  Seq(
    ("org.portable-scala" %% reflect % "1.1.2").cross(CrossVersion.for3Use2_13),
    "io.github.cquiroz"   %% sjt     % "2.5.0"
  )
}
