import java.util.HashSet;
import java.util.Set;

public class PaniniAlbum {
  private final Set<Integer> collection;

  public PaniniAlbum() {
    collection = new HashSet<>();
  }

  public void openAndAddPack() {
    final PaniniPack pack = new PaniniPack();
    collection.addAll(pack);
  }

  public int numberMissing() {
    return Config.TOTAL_STICKERS - collection.size();
  }
}
