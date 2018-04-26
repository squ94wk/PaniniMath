public class Application {
  public static void main(String[] args) throws InterruptedException {
    int runs = 10000;
    if (args.length == 1 && args[0].matches("\\d{1,8}")) {
      runs = Integer.parseInt(args[0]);
    }

    StatisticalTestCollector test = new StatisticalTestCollector(runs);

    test.runTest();
    Thread.sleep(100);
    System.out.println();
    System.out.println("Test results:");
    System.out.println(String.format("Average: %.2f", test.getAverage()));
    System.out.println(String.format("Standard deviation: %.2f", test.getStandardDeviation()));
  }
}
