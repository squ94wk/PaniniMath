import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgressBar extends Thread {
  private final int sleepInterval;
  private final long timeAtStart;
  private final int progressWhenFinished;
  private final ProgressMeasurable process;

  public ProgressBar(final ProgressMeasurable process, final int sleepInterval) {

    this.process = process;
    this.progressWhenFinished = process.getProgressWhenFinished();
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
    builder.append(String.format("| %.1f%%  time: %s", ((double) process.getProgress() * 100) / progressWhenFinished, getTimeElapsed()));
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

  private String getTimeElapsed() {
    final StringBuilder format = new StringBuilder();
    long elapsedMilliseconds = System.currentTimeMillis() - timeAtStart;
    if (elapsedMilliseconds > 1000 * 60) {
      format.append("%1$dm ");
    }
    if (elapsedMilliseconds > 1000) {
      format.append("%2$ds ");
    }
    final long seconds = (elapsedMilliseconds / 1000) % 60;
    final long minutes = elapsedMilliseconds / 60 / 1000;
    return String.format(format.toString(), minutes, seconds);
  }
}
