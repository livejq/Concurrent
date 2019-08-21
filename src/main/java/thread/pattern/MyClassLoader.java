package thread.pattern;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author livejq
 * @since 19-8-21
 */
public class MyClassLoader extends ClassLoader {
  private String loadPath;

  MyClassLoader(ClassLoader cl) {
    super(cl);
  }

  public void setPath(String path) {
    this.loadPath = path;
  }

  @Override
  protected Class findClass(String className) throws ClassNotFoundException {
    FileInputStream fis = null;
    byte[] data = null;
    ByteArrayOutputStream baos = null;

    try {
      fis =
          new FileInputStream(new File(loadPath + className.replaceAll("\\.", "\\\\") + ".class"));
      baos = new ByteArrayOutputStream();
      int tmpByte = 0;
      while ((tmpByte = fis.read()) != -1) {
        baos.write(tmpByte);
      }
      data = baos.toByteArray();
    } catch (IOException e) {
      throw new ClassNotFoundException("class is not found:" + className, e);
    } finally {
      try {
        if (fis != null) {
          fis.close();
        }
        if (baos != null) {
          baos.close();
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return defineClass(className, data, 0, data.length);
  }
}
