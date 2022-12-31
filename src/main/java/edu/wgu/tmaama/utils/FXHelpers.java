package edu.wgu.tmaama.utils;

import javafx.scene.control.TableView;

/**
 * Static helper class for JavaFx related things.
 */
public class FXHelpers {
  /**
   * Dynamically decides the table column width to make sure that the TableView is filled out evenly between every column.
   * @param table
   */
  public static void setTableWidth(TableView<?> table) {
    // Fill the columns to the correct lengths.
    double tableWidth = table.getWidth();
    var columns = table.getColumns();
    double calcColWidth = tableWidth / columns.size();
    for (var col : columns) {
      col.setPrefWidth(calcColWidth);
    }
  }
}
