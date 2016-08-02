package common;

/**
 * Defines required public methods for classes that define Window objects.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public interface Window_ {

  public abstract String getName();

  public abstract String getLocator();

  public abstract void setLocator(String locator);

  public abstract void setLocator();

  public abstract int getLoadTimeout();

  public abstract int getPageRedrawDelay();

  public abstract String getMetadata();

  public abstract void close();

  public abstract void select();

  //Convenience method for those who are used to calling the Selenium selectWindow method
  public abstract void selectWindow();

  public abstract String getTitle();
  
  //These three have been moved to the GPage interface (and modified)
  //public abstract void waitForPopUpToOpen(GWindow window);
  
  //public abstract void waitForPopUpToOpen(GWindow window, int timeout);

  //public abstract void waitForPopUpToOpen(GWindow window, String timeout);
}