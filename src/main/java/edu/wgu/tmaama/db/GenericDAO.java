package edu.wgu.tmaama.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * An interface for basic Data Access Object functionality.
 * @param <T>
 */
public interface GenericDAO<T> {
  public T insert(T item) throws SQLException;

  public T findByID(int id) throws SQLException;
  public ArrayList<T> findAll() throws SQLException;
  public T update(T item) throws SQLException;

  public boolean deleteByID(int id) throws SQLException;

  public T getInstanceFromResultSet(ResultSet resultSet) throws SQLException;
}
