import org.apache.commons.math3.random.MersenneTwister;

import java.util.HashSet;

public class PaniniPack extends HashSet<Integer> {
  private static final MersenneTwister rng = new MersenneTwister(System.currentTimeMillis());

  public PaniniPack() {
    super(Config.STICKERS_PER_PACK * 5);
    while (this.size() < Config.STICKERS_PER_PACK) {
      final int newSticker = randomSticker();
      this.add(newSticker);
    }
  }

  private static synchronized int randomSticker() {
    int totalStickers = Config.TOTAL_STICKERS;
    return (rng.nextInt() % totalStickers + totalStickers) % totalStickers;
  }
}
