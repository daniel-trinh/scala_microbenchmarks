package org.example

import com.google.caliper.{Runner => CaliperRunner}
import com.danieltrinh.benchmarks._

object Runner {

  // main method for IDEs, from the CLI you can also run the caliper Runner directly
  // or simply use SBTs "run" action
  def main(args: Array[String]) {
    // we simply pass in the CLI args,
    // we could of course also just pass hardcoded arguments to the caliper Runner

    // TODO: write microbench that tests map + recover vs case pattern matching.

    // CaliperRunner.main(classOf[DefVsLazyValVsVal], args: _*)
    // CaliperRunner.main(classOf[DefaultArgsVsSingletonADT], args: _*)
    // CaliperRunner.main(classOf[TraitMixinBenchmark], args: _*)
    // CaliperRunner.main(classOf[ExceptionHandlingBenchmark], args: _*)
    // CaliperRunner.main(classOf[TryVsFutureBenchmark], args: _*)
    // CaliperRunner.main(classOf[ExceptionObjectCreationBenchmark], args: _*)
    // CaliperRunner.main(classOf[LazyCollectionsVsRegularCollections], args: _*)
    // CaliperRunner.main(classOf[FilterTakes], args: _*)
    // CaliperRunner.main(classOf[Pack], args: _*)
    // CaliperRunner.main(classOf[StringVsStringBuilder], args: _*)
    CaliperRunner.main(classOf[ViewZipForeach], args: _*)
    // CaliperRunner.main(classOf[UUIDVsCounterVsTimestampBenchmark], args: _*)
    // CaliperRunner.main(classOf[ReadingValsVsLazyValsVsDefs], args: _*)
  }
}