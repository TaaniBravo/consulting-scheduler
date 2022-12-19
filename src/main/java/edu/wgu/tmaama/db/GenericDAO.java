package edu.wgu.tmaama.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface GenericDAO<T> {
  public T insert(T item) throws SQLException;

  public T findByID(int id) throws SQLException;
  //    public ArrayList<T> find(ArrayList<String> attributes, HashMap<String, String> where) throws
  // SQLException;
  public ArrayList<T> findAll() throws SQLException;
  //    public List<T> findAll() throws SQLException;
  public T update(T item) throws SQLException;

  public boolean deleteByID(int id) throws SQLException;

  public T getInstanceFromResultSet(ResultSet resultSet) throws SQLException;
}
