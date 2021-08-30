import Dependencies._

ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version := "0.0.1"
ThisBuild / organization := "com.seank"
ThisBuild / organizationName := "seank"
ThisBuild / scalafixDependencies += Libraries.organizeImports

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = (project in file("."))
  .settings(name := "console-drawer")
  .aggregate(core, tests)

val scalafixCommonSettings = inConfig(IntegrationTest)(scalafixConfigSettings(IntegrationTest))

lazy val tests = (project in file("modules/tests"))
  .configs(IntegrationTest)
  .settings(
    name := "console-drawer-test-suite",
    IntegrationTest / envVars := Map("VH_APP_ENV" -> "test"),
    IntegrationTest / fork := true,
    scalacOptions ++= List("-Ymacro-annotations", "-Yrangepos", "-Wconf:cat=unused:info", "-Ywarn-dead-code",
      "-Ywarn-unused", "-Ywarn-value-discard"),
    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
    Defaults.itSettings,
    scalafixCommonSettings,
    libraryDependencies ++= Seq(
      CompilerPlugin.kindProjector,
      CompilerPlugin.betterMonadicFor,
      Libraries.catsLaws,
      Libraries.log4catsNoOp,
      Libraries.log4catsTesting,
      Libraries.monocleLaw,
      Libraries.weaverCats,
      Libraries.weaverDiscipline,
      Libraries.weaverSpecs,
      Libraries.weaverScalaCheck,
      Libraries.wiremock,
      Libraries.specs2,
      Libraries.apacheCommon,
      Libraries.spireMathCore,
      Libraries.spireMathCheck,
      Libraries.spireMathProps,
      Libraries.scodecCats
    )
  )
  .dependsOn(core)

lazy val core = (project in file("modules/core"))
  .enablePlugins(DockerPlugin)
  .enablePlugins(AshScriptPlugin)
  .settings(
    name := "console-drawer-core",
    Docker / packageName := "console_drawer-",
    scalacOptions ++= List("-Ymacro-annotations", "-Yrangepos", "-Wconf:cat=unused:info", "-Ywarn-dead-code",
      "-Ywarn-unused", "-Ywarn-value-discard"),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    Defaults.itSettings,
    scalafixCommonSettings,
    dockerBaseImage := "openjdk:11-jre-slim-buster",
    dockerExposedPorts ++= Seq(8080),
    makeBatScripts := Seq(),
    dockerUpdateLatest := true,
    libraryDependencies ++= Seq(
      CompilerPlugin.kindProjector,
      CompilerPlugin.betterMonadicFor,
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.derevoCore,
      Libraries.derevoCats,
      Libraries.derevoCirce,
      Libraries.log4cats,
      Libraries.logback % Runtime,
      Libraries.monocleCore,
      Libraries.squants,
      Libraries.scoptL
    )
  )

addCommandAlias("runLinter", ";scalafixAll --rules OrganizeImports")