package edu.wgu.tmaama.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Logger {
  private static final String FILE_PATH = "login_activity.txt";

  public Logger() throws IOException {
    if (Files.notExists(Path.of(FILE_PATH))) {
      Files.createFile(Path.of(FILE_PATH));
    }
  }

  public void log(String str) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
    writer.append(str).append("\n");
    writer.close();
  }
}
