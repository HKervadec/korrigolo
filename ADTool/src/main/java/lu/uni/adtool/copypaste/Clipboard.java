package lu.uni.adtool.copypaste;

public class Clipboard
{
  private Transferable content;

  /**
   * @return the content
   */
  public Transferable getContent()
  {
    return content;
  }

  /**
   * @param content the content to set
   */
  public void setContent(Transferable content)
  {
    this.content = content;
  }
}
