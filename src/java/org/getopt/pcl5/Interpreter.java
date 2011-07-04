package org.getopt.pcl5;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.getopt.pcl5.HPGLInterpreter.cmd.CmdUnknownHPGL;
import org.getopt.pcl5.HPGLInterpreter.cmd.CommandHPGL;
import org.getopt.pcl5.PCL5Interpreter.ControlCodes;
import org.getopt.pcl5.PCL5Interpreter.IInitialSetup;
import org.getopt.pcl5.PCL5Interpreter.IParse;
import org.getopt.pcl5.PCL5Interpreter.cmd.BaseCommandPCL5;
import org.getopt.pcl5.PCL5Interpreter.cmd.CmdEscUnknownPCL5;
import org.getopt.pcl5.PCL5Interpreter.cmd.CmdExtendedEscUnknownPCL5;
import org.getopt.pcl5.PCL5Interpreter.cmd.CmdUnknownPCL5;
import org.getopt.pcl5.PCL5Interpreter.cmd.CommandPCL5;
import org.getopt.pcl5.PCL5Interpreter.cmd.EscCommandPCL5;
import org.getopt.pcl5.PCL5Interpreter.cmd.EscExtendedCommandPCL5;
import org.getopt.pcl5.PJLInterpreter.cmd.BaseCommandPJL;
import org.getopt.pcl5.PJLInterpreter.cmd.CmdEscUnknownPJL;
import org.getopt.pcl5.PJLInterpreter.cmd.CmdUnknownPJL;
import org.getopt.pcl5.PJLInterpreter.cmd.CommandPJL;
import org.getopt.pcl5.PJLInterpreter.cmd.EscCommandPJL;

/**
 * This class is main class for printer parser, instance of this class will
 * parse printer result stream
 */
public class Interpreter implements IParse, IInitialSetup {
  PrinterState _printerState = new PrinterState();

  /**
   * Internal helper class to read integer from stream until non-digit We need
   * class because of lack of pass-by-ref in java
   */
  class ReadParameterFormStream {
    String _parameter;
    char _cmd;
    char _subfamily;

    void readParameter(InputStream in, char subfamily) throws IOException {
      StringBuilder str = new StringBuilder();

      // it's possible that we receive command without family
      if (subfamily == '-' || subfamily == '+' || Character.isDigit(subfamily)) {
        _subfamily = 0;
        str.append(subfamily);
      } else
        _subfamily = subfamily;

      char c = 0;
      _parameter = null;
      int data = in.read();
      while (data != -1) {
        c = (char) data;
        if (c == '-' || Character.isDigit(c))
          str.append(c);
        else
          break;

        data = in.read();
      }

      _cmd = (char) data;

      if (str.length() > 0)
        _parameter = str.toString();
    }

    /**
     * @return Returns the _cmd.
     */
    public char getCmd() {
      return _cmd;
    }

    /**
     * @return Returns the _parameter.
     */
    public String getParameter() {
      return _parameter;
    }

    public char getSubfamily() {
      return _subfamily;
    }
  }

  interface IParser {
    public void parse(int data, InputStream in) throws IOException;
  }

  /**
   * Internal class that implements interpretation of printer commands. First
   * loads all Cmd* classes and creates 3 collection for 3 different types of
   * commands There are 2 collections for each type, hashed for often used
   * collections and ArrayLists
   */
  class PCL5Parser implements IParser {
    private final static String RESOURCE_NAME = "res/commands.properties";

    ArrayList _cmdParsers = new ArrayList();
    HashMap _cmdParsersHash = new HashMap();
    ArrayList _cmdEscParsers = new ArrayList();
    HashMap _cmdEscParsersHash = new HashMap();
    ArrayList _cmdExtendedEscParsers = new ArrayList();
    HashMap _cmdExtendedEscParsersHash = new HashMap();

    CmdUnknownPCL5 cmdUnknown = new CmdUnknownPCL5(_printerState);
    CmdEscUnknownPCL5 cmdEscUnknown = new CmdEscUnknownPCL5(_printerState);
    CmdExtendedEscUnknownPCL5 cmdExtendedEscUnknown = new CmdExtendedEscUnknownPCL5(
            _printerState);

    /**
     * Very long contructor that creates Cmd* collections
     * 
     * @throws IOException
     */
    public PCL5Parser() throws IOException {
      PackageLoader packageLoader = new PackageLoader(RESOURCE_NAME,
              "PCL5Interpreter");
      Class[] constructorParams = { PrinterState.class };
      Object[] ps = { _printerState };

      Iterator iter = packageLoader.getClassList().iterator();
      while (iter.hasNext()) {
        String clsName = (String) iter.next();

        try {
          Class cls = Class.forName(clsName);
          BaseCommandPCL5 o;
          if (cls.getSuperclass() == CommandPCL5.class) {
            Constructor constr = cls.getDeclaredConstructor(constructorParams);
            o = (BaseCommandPCL5) constr.newInstance(ps);

            char c = o.getCommandCode();
            if (c != 0)
              _cmdParsersHash.put(new Integer(c), o);
            else
              _cmdParsers.add(o);
          } else if (cls.getSuperclass() == EscCommandPCL5.class) {
            Constructor constr = cls.getDeclaredConstructor(constructorParams);
            o = (BaseCommandPCL5) constr.newInstance(ps);

            char c = o.getCommandCode();
            if (c != 0)
              _cmdEscParsersHash.put(new Integer(c), o);
            else
              _cmdEscParsers.add(o);
          } else if (cls.getSuperclass() == EscExtendedCommandPCL5.class) {
            Constructor constr = cls.getDeclaredConstructor(constructorParams);
            o = (BaseCommandPCL5) constr.newInstance(ps);

            char c = o.getCommandCode();
            if (c != 0)
              _cmdExtendedEscParsersHash.put(new Integer(c), o);
            else
              _cmdExtendedEscParsers.add(o);
          } else if (cls.getSuperclass() == BaseCommandPCL5.class) {
            // Unknown command classes
          } else {
            System.err.println("Incompatible class found: " + clsName);
          }
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        } catch (InstantiationException e) {
          throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        } catch (SecurityException e) {
          throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
          throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
          throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
          throw new RuntimeException(e);
        }
      }
    }

    private boolean isExtendedCommand(char family) {
      return family == '%' || family == '&' || family == '(' || family == ')'
              || family == '*';
    }

    /**
     * Parser method for Esc commands, finds command that implements command and
     * executes
     * 
     * @param in
     *          stream to parse
     * 
     * @throws IOException
     */
    public void parse(int data, InputStream in) throws IOException {
      char c = (char) data;

      if (c >= ' ' && c != ControlCodes.DEL) {
        _printerState.addText(c);
        return;
      }

      if (c != ControlCodes.ESC) {
        parse(c);
      } else {
        char family = (char) in.read();

        if (isExtendedCommand(family)) // extended command
        {
          executeExtendedEscCommand(in, family);
        } else {
          executeEscCommand(in, family);
        }
      }
    }

    /**
     * @param in
     * @param family
     * @throws IOException
     */
    private void executeExtendedEscCommand(InputStream in, char family)
            throws IOException {
      Iterator iter;
      char subfamily = (char) in.read();
      char command;

      do {
        _paramReader.readParameter(in, subfamily);
        command = _paramReader.getCmd();
        String parameter = _paramReader.getParameter();
        subfamily = _paramReader.getSubfamily();

        EscExtendedCommandPCL5 cmd;
        // TODO: needs correct implementation
        // cmd = (EscExtendedCommand)cmdExtendedEscParsersHash.get(new
        // Integer(data));

        // if (cmd != null)
        // {
        // cmd.execute(data, in);
        // return;
        // }

        iter = _cmdExtendedEscParsers.iterator();
        boolean found = false;

        while (iter.hasNext()) {
          cmd = (EscExtendedCommandPCL5) iter.next();
          if (cmd.execute(family, subfamily, parameter,
                  Character.toUpperCase(command), in)) {
            found = true;
            break;
          }
        }

        if (!found)
          cmdExtendedEscUnknown.execute(family, subfamily, parameter, command,
                  in);

      } while (Character.isLowerCase(command));
    }

    /**
     * @param in
     * @param family
     * @throws IOException
     */
    private void executeEscCommand(InputStream in, char family)
            throws IOException {
      Iterator iter;
      // this commands was tested
      final String covered = "E";
      if (covered.indexOf(family) == -1)
        notCovered(family);

      EscCommandPCL5 cmd = (EscCommandPCL5) _cmdEscParsersHash.get(new Integer(
              family));

      if (cmd != null) {
        cmd.execute(family, in);
        return;
      }

      iter = _cmdEscParsers.iterator();

      while (iter.hasNext()) {
        cmd = (EscCommandPCL5) iter.next();
        if (cmd.execute(family, in))
          return;
      }
      cmdEscUnknown.execute(family, in);
      return;
    }

    /**
     * Parser method for direct (non-Esc) commands
     * 
     * @param data
     *          command to execute
     */
    private void parse(int data) {
      final String covered = "\r";
      if (covered.indexOf(data) == -1)
        notCovered(data);

      CommandPCL5 cmd = (CommandPCL5) _cmdParsersHash.get(new Integer(data));

      if (cmd != null) {
        cmd.execute(data);
        return;
      }

      Iterator iter = _cmdParsers.iterator();

      while (iter.hasNext()) {
        cmd = (CommandPCL5) iter.next();
        if (cmd.execute(data))
          return;
      }

      cmdUnknown.execute(data);
    }

    /**
     * Not covered methods trace
     * 
     * @param c
     *          printer command
     */
    private void notCovered(int c) {
      _printerState.trace(this, "Command not tested: " + c);
    }
  }

  class PJLParser implements IParser {
    private final static String RESOURCE_NAME = "res/pjlcommands.properties";

    ArrayList _cmdParsers = new ArrayList();
    HashMap _cmdParsersHash = new HashMap();
    ArrayList _cmdEscParsers = new ArrayList();

    CmdUnknownPJL _cmdUnknown = new CmdUnknownPJL(_printerState);
    CmdEscUnknownPJL _cmdEscUnknown = new CmdEscUnknownPJL(_printerState);

    /**
     * Very long contructor that creates Cmd* collections
     * 
     * @throws IOException
     */
    public PJLParser() throws IOException {
      PackageLoader packageLoader = new PackageLoader(RESOURCE_NAME,
              "PJLInterpreter");
      Class[] constructorParams = { PrinterState.class };
      Object[] ps = { _printerState };

      Iterator iter = packageLoader.getClassList().iterator();
      while (iter.hasNext()) {
        String clsName = (String) iter.next();

        try {
          Class cls = Class.forName(clsName);
          BaseCommandPJL o;
          if (cls.getSuperclass() == CommandPJL.class) {
            Constructor constr = cls.getDeclaredConstructor(constructorParams);
            o = (BaseCommandPJL) constr.newInstance(ps);

            String cmd = o.getCommandString();
            if (cmd != null)
              _cmdParsersHash.put(cmd, o);
            else
              _cmdParsers.add(o);
          } else if (cls.getSuperclass() == EscCommandPJL.class) {
            Constructor constr = cls.getDeclaredConstructor(constructorParams);
            o = (BaseCommandPJL) constr.newInstance(ps);

            _cmdEscParsers.add(o);
          } else {
            System.err.println("Incompatible class found: " + clsName);
          }
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        } catch (InstantiationException e) {
          throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        } catch (SecurityException e) {
          throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
          throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
          throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
          throw new RuntimeException(e);
        }
      }
    }

    /**
     * Parser method for Esc commands, finds command that implements command and
     * executes
     * 
     * @param in
     *          stream to parse
     * 
     * @throws IOException
     */
    public void parse(int data, InputStream in) throws IOException {
      char c = (char) data;

      if (c == '@') // extended command
      {
        executePJLCommand(in);
      } else if (c != ControlCodes.ESC) {
        executeEscCommand(in);
      } else {
        _printerState.addText(c);
      }
    }

    /**
     * @param in
     * @param family
     * @throws IOException
     */
    private void executeEscCommand(InputStream in) throws IOException {
      char family = (char) in.read();

      Iterator iter;
      // this commands was tested
      iter = _cmdEscParsers.iterator();

      while (iter.hasNext()) {
        EscCommandPJL cmd = (EscCommandPJL) iter.next();
        if (cmd.execute(family, in))
          return;
      }
      _cmdEscUnknown.execute(family, in);
      return;
    }

    String readLine(InputStream in) throws IOException {
      StringBuilder sb = new StringBuilder();

      int n = in.read();

      while (n != -1 && n != ControlCodes.LF) // EOF or LF
      {
        if (n != ControlCodes.CR)
          sb.append((char) n);

        n = in.read();
      }

      return sb.toString();
    }

    /**
     * @param in
     * @param family
     * @throws IOException
     */
    private void executePJLCommand(InputStream in) throws IOException {
      String expression = readLine(in);
      String[] tokens = expression.split("\\p{Blank}+");

      if (!tokens[0].equalsIgnoreCase("PJL"))
        throw new RuntimeException("Error in PJL.");

      CommandPJL cmd = (CommandPJL) _cmdParsersHash.get(tokens[1]);

      if (cmd != null) {
        cmd.execute(tokens);
        return;
      }

      Iterator iter = _cmdParsers.iterator();

      while (iter.hasNext()) {
        cmd = (CommandPJL) iter.next();
        if (cmd.execute(tokens))
          return;
      }
      _cmdUnknown.execute(tokens);

      return;
    }
  }

  class HPGLParser implements IParser {
    private final static String RESOURCE_NAME = "res/hpglcommands.properties";

    ArrayList _cmdParsers = new ArrayList();
    HashMap _cmdParsersHash = new HashMap();

    CmdUnknownHPGL _cmdUnknown = new CmdUnknownHPGL(_printerState);

    /**
     * Very long contructor that creates Cmd* collections
     * 
     * @throws IOException
     */
    public HPGLParser() throws IOException {
      PackageLoader packageLoader = new PackageLoader(RESOURCE_NAME,
              "HPGLInterpreter");
      Class[] constructorParams = { PrinterState.class };
      Object[] ps = { _printerState };

      Iterator iter = packageLoader.getClassList().iterator();
      while (iter.hasNext()) {
        String clsName = (String) iter.next();

        try {
          Class cls = Class.forName(clsName);
          CommandHPGL o;
          if (cls.getSuperclass() == CommandHPGL.class) {
            Constructor constr = cls.getDeclaredConstructor(constructorParams);
            o = (CommandHPGL) constr.newInstance(ps);

            String cmd = o.getCommandString();
            if (cmd != null)
              _cmdParsersHash.put(cmd, o);
            else
              _cmdParsers.add(o);
          } else {
            System.err.println("Incompatible class found: " + clsName);
          }
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        } catch (InstantiationException e) {
          throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        } catch (SecurityException e) {
          throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
          throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
          throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
          throw new RuntimeException(e);
        }
      }
    }

    public void parse(int data, InputStream in) throws IOException {
      char c = (char) data;
      while (data != -1 && (c == ' ' || c == ';')) {
        data = in.read();
        c = (char) data;
      }

      if (data == -1)
        return;
      else if (c == ControlCodes.ESC) {
        executeEscCommand(in);
      }
      if (c <= ' ') // control command
      {
        executeControlCode(c, in);
      } else {
        String cmd = "";
        cmd += c + (char) in.read();

        executeHPGLCommand(cmd, in);
      }
    }

    private void executeControlCode(char c, InputStream in) {
      // TODO Auto-generated method stub

    }

    /**
     * @param in
     * @param family
     * @throws IOException
     */
    private void executeEscCommand(InputStream in) throws IOException {
    }

    /**
     * @param in
     * @param family
     * @throws IOException
     */
    private void executeHPGLCommand(String command, InputStream in)
            throws IOException {
      CommandHPGL cmd = (CommandHPGL) _cmdParsersHash.get(command);

      if (cmd != null) {
        cmd.execute(command, in);
        return;
      }

      Iterator iter = _cmdParsers.iterator();

      while (iter.hasNext()) {
        cmd = (CommandHPGL) iter.next();
        if (cmd.execute(command, in))
          return;
      }
      _cmdUnknown.execute(command, in);

      return;
    }
  }

  private PCL5Parser _pcl5Parser;
  private PJLParser _pjlParser;
  private HPGLParser _hpglParser;
  private IParser _activeParser;

  ReadParameterFormStream _paramReader;

  public Interpreter() throws IOException {
    _pcl5Parser = new PCL5Parser();
    _pjlParser = new PJLParser();
    _hpglParser = new HPGLParser();

    _paramReader = new ReadParameterFormStream();
  }

  // ==================================================================
  // IParse
  // ==================================================================
  /*
   * (non-Javadoc)
   * com.ccginc.pcl5.PCL5Interpreter.IParse#Parse(java.io.InputStream,
   * com.ccginc.pcl5.PCL5Interpreter.IPrint)
   */
  public void parse(InputStream in, IPrint print) throws IOException {
    _activeParser = _pcl5Parser;

    print.processingStart();

    _printerState.setPrinter(print);
    _printerState.reset();

    int data = in.read();

    while (data != -1) {
      _activeParser = getActiveParser();
      _activeParser.parse(data, in);

      data = in.read();
    }

    print.processingEnd();
  }

  private IParser getActiveParser() {
    switch (_printerState.getActiveLanguage()) {
    case PrinterState.Language.PCL5:
      return _pcl5Parser;

    case PrinterState.Language.PJL:
      return _pjlParser;

    case PrinterState.Language.HPGL:
      return _hpglParser;
    }
    return null;
  }

  public void setBoundBoxColor(boolean boudBox) {
    _printerState.setBoundBoxColor(boudBox);
  }
}
