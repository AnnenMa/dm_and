import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.*;

/**
 *  Vereinfachte Handhabung grundlegender IO-Funktionalität im Rahmen der
 *  Veranstaltung A&D der Technischen Fakultät, Universität Bielefeld
 *  Ursprünglich bereitgestellt für die Veranstaltung SE1 der TU Kaiserlsautern
 */
public class IO {
  public static void print(Object object) {
    out.print(object);
  }

  public static void println(Object object) {
    out.println(object);
  }

  public static int readInt() {
    try {
      return new Scanner(in).nextInt();
    }
    catch(Throwable ex) {
      throw new RuntimeException("Input not an integer", ex);
    }
  }

  public static String readString() {
    try {
      return new Scanner(in).nextLine();
    }
    catch(Throwable ex) {
      throw new RuntimeException("Error in reading input", ex);
    }
  }
  
  public static String [] readFileToStringArray(String path) {

	  ArrayList<String> al = new ArrayList<String>();
	  try {
		 BufferedReader br = new BufferedReader(new FileReader(path));
	     String line = null;
	     while ((line = br.readLine()) != null) {
 	    	 al.add(line);
	     }
	     br.close();
	     return al.toArray(new String[0]);
	  }
	  catch(Throwable ex) {
	     throw new RuntimeException("Error in reading input", ex);
	  }
  }

  public static char readChar() {
    try {
      String result = readString();

      if (result.length() > 1)
        out.println("*** Warning: Input too long.\n"+
                    "***          Rest of input discarded.");

      return result.charAt(0);
    }
    catch(IndexOutOfBoundsException ex) {
      throw new RuntimeException("Empty input",ex);
    }
  }
}