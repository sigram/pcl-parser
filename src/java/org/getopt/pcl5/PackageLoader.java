/*
 * Created on 2004-09-23
 *
 */
package org.getopt.pcl5;

import java.io.*;
import java.util.*;

/**
 * Package class loader, this class tries to load all class in package with
 * names that fit into class schema name.
 */
public class PackageLoader {
  // private String classSchemaName = "Cmd.*";
  // private String classExt = "\\.class";
  // private Pattern clsPattern = Pattern.compile(classSchemaName + classExt);

  // private final static String RESOURCE_NAME = "res/commands.properties";

  private ArrayList classList = new ArrayList();

  /**
   * Loads list of class names from resourceName file.
   * 
   * @throws IOException
   */
  public PackageLoader(String resourceName, String pkg) throws IOException {
    InputStream is = this.getClass().getResourceAsStream(resourceName);
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));

    String pckgName = this.getClass().getPackage().getName();
    String line = rd.readLine();
    while (line != null) {
      classList.add(pckgName + "." + pkg + ".cmd." + line);
      line = rd.readLine();
    }
  }

  // public PackageLoader()
  // {
  // String pckgName = this.getClass().getPackage().getName();
  //
  // String pckgPath = new String(pckgName);
  //
  // if (!pckgPath.startsWith("/"))
  // {
  // pckgPath = "/" + pckgPath;
  // }
  // pckgPath = pckgPath.replace('.','/');
  //
  // URL url = this.getClass().getResource(pckgPath);
  //
  // // Happens only if the jar file is not well constructed, i.e.
  // // if the directories do not appear alone in the jar file
  // if (url == null)
  // throw new RuntimeException("Unable to open package: " + pckgName);
  //
  // File directory = new File(url.getFile());
  // if (directory.exists())
  // loadClassesFromDirectory(pckgName, directory);
  // else
  // loadClassesFromJar(url);
  // }
  //
  // private void loadClassesFromDirectory(String pckgName, File directory)
  // {
  // // Get the list of the files contained in the package
  // String[] files = directory.list();
  //
  // for (int i = 0; i < files.length; i++)
  // {
  // // we are only interested in .class files that fits to pattern
  // Matcher m = clsPattern.matcher(files[i]);
  //
  // if (m.matches())
  // {
  // // removes the .class extension
  // String classname = files[i].substring(0, files[i].length() - 6);
  // classList.add(pckgName + "." + classname);
  // }
  // }
  // }
  //
  // // FIXME: not tested yet!!
  // private void loadClassesFromJar(URL url)
  // {
  // try
  // {
  // JarURLConnection conn = (JarURLConnection)url.openConnection();
  //
  // String starts = conn.getEntryName();
  // JarFile jfile = conn.getJarFile();
  //
  // Enumeration e = jfile.entries();
  //
  // while (e.hasMoreElements())
  // {
  // ZipEntry entry = (ZipEntry)e.nextElement();
  //
  // String entryname = entry.getName();
  //
  // Matcher m = clsPattern.matcher(entryname);
  //
  // if (m.matches())
  // {
  // String classname = entryname.substring(0, entryname.length() - 6);
  //
  // if (classname.startsWith("/"))
  // classname = classname.substring(1);
  //
  // classname = classname.replace('/','.');
  //
  // classList.add(classname);
  // }
  // }
  // }
  // catch (IOException ex)
  // {
  // System.err.println(ex);
  // }
  // }

  /**
   * @return Returns the classList.
   */
  public ArrayList getClassList() {
    return classList;
  }
}
