import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static com.google.common.base.Preconditions.checkArgument;

public class StatisticalTestCollector implements ProgressMeasurable {
  private final int rounds;
  private final int[] results;
  private volatile int progress = 0;
  private Double average;
  private Double standardDeviation;
  private Double variance;

  public StatisticalTestCollector(final int rounds) {
    checkArgument(rounds > 0, "rounds must be greater than 0");
    this.rounds = rounds;
    results = new int[rounds];
  }

  public void runTest() {
    ProgressBar progressBar = new ProgressBar(this, 50);
    progressBar.start();

    IntStream parallelStream = StreamSupport.intStream(IntStream.range(0, rounds).spliterator(),
        true);

    parallelStream.forEach(i -> {
      final Collector collector = new Collector();
      collector.collectAllCards();
      results[i] = collector.getBoughtPacks();
      progress++;
    });

    progressBar.interrupt();
  }

  public double getAverage() {
    if (average == null) {
      average = Arrays.stream(results).average().getAsDouble();
    }
    return average;
  }

  public double getStandardDeviation() {
    if (standardDeviation == null) {
      standardDeviation = Math.sqrt(getVariance());
    }
    return standardDeviation;
  }

  public double getVariance() {
    if (variance == null) {
      variance = Arrays.stream(results).mapToDouble(x -> (double) x)
          .map(x -> Math.pow(x - getAverage(), 2d))
          .sum() / rounds;
    }
    return variance;
  }

  @Override
  public int getProgress() {
    return progress;
  }

  @Override
  public int getProgressWhenFinished() {
    return rounds;
  }
}
