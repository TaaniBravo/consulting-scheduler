package edu.wgu.tmaama.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Simple logger class.
 */
public class Logger {
  private static final String FILE_PATH = "login_activity.txt";

  /**
   * If the file doesn't exist we make sure to create it.
   * @throws IOException
   */
  public Logger() throws IOException {
    if (Files.notExists(Path.of(FILE_PATH))) {
      Files.createFile(Path.of(FILE_PATH));
    }
  }

  /**
   * Logs the string passed into it into the FILE_PATH.
   * @param str
   * @throws IOException
   */
  public void log(String str) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
    writer.append(str).append("\n");
    writer.close();
  }
}
