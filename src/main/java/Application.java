public class Application {
  public static void main(String[] args) throws InterruptedException {
    StatisticalTestCollector test = new StatisticalTestCollector(1000000);

    test.runTest();
    Thread.sleep(100);
    System.out.println();
    System.out.println("Test results:");
    System.out.println(String.format("Average: %.2f", test.getAverage()));
    System.out.println(String.format("Standard deviation: %.2f", test.getStandardDeviation()));
  }
}
