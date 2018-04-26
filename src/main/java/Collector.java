public class Collector {
  private int boughtPacks = 0;
  private final PaniniAlbum album;

  public Collector() {
    album = new PaniniAlbum();
  }

  public void collectAllCards() {
    while (album.numberMissing() > 0) {
      album.openAndAddPack();
      boughtPacks++;
    }
  }

  public int getBoughtPacks() {
    return boughtPacks;
  }
}
