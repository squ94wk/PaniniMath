import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

public class ProgressBar extends Thread {
  private final int sleepInterval;
  private final long timeAtStart;
  private final int progressWhenFinished;
  private final ProgressMeasurable process;

  public ProgressBar(final ProgressMeasurable process, final int sleepInterval) {
    checkArgument(process != null, "Process must not be null.");
    this.process = process;
    this.progressWhenFinished = process.getProgressWhenFinished();
    checkArgument(progressWhenFinished > 0, "progressWhenFinished must be greater than 0.");
    this.sleepInterval = sleepInterval;
    timeAtStart = System.currentTimeMillis();
  }

  private String buildProgressBar() {
    StringBuilder builder = new StringBuilder();
    char next = '>';
    builder.append("\r|");
    for (int i = 0; i < 80; i++) {
      if (process.getProgress() > progressWhenFinished * i / 80) {
        builder.append('=');
      } else {
        builder.append(next);
        next = ' ';
      }
    }
    final double percent = ((double) process.getProgress() * 100) / progressWhenFinished;
    builder.append(String.format("| %.1f%%  time: %s (%s remaining)", percent, getTimeElapsed(), getTimeRemaining()));
    return builder.toString();
  }

  @Override
  public void run() {
    while (!Thread.interrupted()) {
      System.out.print(buildProgressBar());
      try {
        Thread.sleep(sleepInterval);
      } catch (InterruptedException e) {
        break;
      }
    }

    System.out.print(buildProgressBar());
    System.out.println(String.format("\rdone. Time elapsed: %s.", getTimeElapsed()));
  }

  private String formatTime(long milliseconds) {
    final StringBuilder format = new StringBuilder();
    if (milliseconds > 1000 * 60) {
      format.append("%1$dm ");
    }
    if (milliseconds > 1000) {
      format.append("%2$ds ");
    }
    final long seconds = (milliseconds / 1000) % 60;
    final long minutes = milliseconds / 60 / 1000;
    return String.format(format.toString(), minutes, seconds);
  }

  private String getTimeElapsed() {
    long elapsedMilliseconds = System.currentTimeMillis() - timeAtStart;
    return formatTime(elapsedMilliseconds);
  }

  private String getTimeRemaining() {
    int progress = process.getProgress();
    if (progress == 0) {
      return "";
    }
    long elapsedMilliseconds = System.currentTimeMillis() - timeAtStart;
    double timesRemaining = progressWhenFinished / ((double) progress) - 1;

    return formatTime((int) Math.round(timesRemaining * elapsedMilliseconds));
  }
}
