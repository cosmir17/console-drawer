import sbt._

object Dependencies {

  object V {
    val cats          = "2.6.1"
    val catsEffect    = "3.2.3"
    val catsRetry     = "2.1.0"
    val derevo        = "0.12.6"
    val log4cats      = "2.1.1"
    val monocle       = "3.0.0-M6"
    val squants       = "1.8.1"

    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.13.0"
    val logback          = "1.2.3"
    val organizeImports  = "0.5.0"
    val scopt            = "4.0.1"

    val weaver        = "0.7.3"
    val specs2        = "4.11.0"
    val wiremock      = "2.27.2"
    val apacheCommon  = "2.8.0"
    val spireMath     = "0.2.6"
    val scodecCats    = "1.1.0"
  }

  object Libraries {
    def derevo(artifact: String): ModuleID = "tf.tofu"    %% s"derevo-$artifact" % V.derevo

    val cats       = "org.typelevel"    %% "cats-core"   % V.cats
    val catsEffect = "org.typelevel"    %% "cats-effect" % V.catsEffect
    val catsRetry  = "com.github.cb372" %% "cats-retry"  % V.catsRetry
    val squants    = "org.typelevel"    %% "squants"     % V.squants

    val derevoCore  = derevo("core")
    val derevoCats  = derevo("cats")
    val derevoCirce = derevo("circe-magnolia")

    val monocleCore = "com.github.julien-truffaut" %% "monocle-core" % V.monocle

    val log4cats        = "org.typelevel" %% "log4cats-slf4j" % V.log4cats
    val log4catsTesting = "org.typelevel" %% "log4cats-testing" % V.log4cats
    val scoptL = "com.github.scopt" %% "scopt" % V.scopt

    // Runtime
    val logback  = "ch.qos.logback" % "logback-classic" % V.logback

    // Test
    val catsLaws         = "org.typelevel"              %% "cats-laws"         % V.cats
    val log4catsNoOp     = "org.typelevel"              %% "log4cats-noop"     % V.log4cats
    val monocleLaw       = "com.github.julien-truffaut" %% "monocle-law"       % V.monocle
    val weaverCats       = "com.disneystreaming"        %% "weaver-cats"       % V.weaver
    val weaverDiscipline = "com.disneystreaming"        %% "weaver-discipline" % V.weaver
    val weaverScalaCheck = "com.disneystreaming"        %% "weaver-scalacheck" % V.weaver
    val weaverSpecs      = "com.disneystreaming"        %% "weaver-specs2"     % V.weaver
    val specs2           = "org.specs2"                 %% "specs2-core"       % V.specs2
    val wiremock         = "com.github.tomakehurst"     %  "wiremock"          % V.wiremock
    val spireMathCore    = "org.spire-math"             %% "antimirov-core"    % V.spireMath
    val spireMathCheck   = "org.spire-math"             %% "antimirov-check"   % V.spireMath
    val spireMathProps   = "org.spire-math"             %% "antimirov-props"   % V.spireMath
    val apacheCommon     = "commons-io"                 %  "commons-io"        % V.apacheCommon
    val scodecCats       = "org.scodec"                 %% "scodec-cats"       % V.scodecCats

    // Scalafix rules
    val organizeImports = "com.github.liancheng" %% "organize-imports" % V.organizeImports
  }

  object CompilerPlugin {
    val betterMonadicFor = compilerPlugin(
      "com.olegpy" %% "better-monadic-for" % V.betterMonadicFor
    )
    val kindProjector = compilerPlugin(
      "org.typelevel" % "kind-projector" % V.kindProjector cross CrossVersion.full
    )
  }

}
