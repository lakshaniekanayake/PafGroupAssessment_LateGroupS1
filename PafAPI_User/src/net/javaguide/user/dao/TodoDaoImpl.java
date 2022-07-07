package net.javaguide.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import net.javaguide.user.model.Todo;
import net.javaguide.user.utils.JDBCUtils;



public class TodoDaoImpl implements TodoDao {

	private static final String INSERT_TODOS_SQL = "INSERT INTO todos"
			+ "  (title, username, description,amount, target_date,  is_done) VALUES " + " (?, ?, ?, ?, ?,?);";

	private static final String SELECT_TODO_BY_ID = "select id,title,username,description,amount,target_date,is_done from todos where id =?";
	private static final String SELECT_ALL_TODOS = "select * from todos";
	private static final String DELETE_TODO_BY_ID = "delete from todos where id = ?;";
	private static final String UPDATE_TODO = "update todos set title = ?, username= ?, description =?, amount=?, target_date =?, is_done = ? where id = ?;";

	public TodoDaoImpl() {
	}

	@Override
	public void insertTodo(Todo todo) throws SQLException {
		System.out.println(INSERT_TODOS_SQL);
		
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TODOS_SQL)) {
			preparedStatement.setString(1, todo.getTitle());
			preparedStatement.setString(2, todo.getUsername());
			preparedStatement.setString(3, todo.getDescription());
			preparedStatement.setString(4, todo.getAmount());
			preparedStatement.setDate(5, JDBCUtils.getSQLDate(todo.getTargetDate()));
			preparedStatement.setBoolean(6, todo.getStatus());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
	}

	@Override
	public Todo selectTodo(long todoId) {
		Todo todo = null;
		
		try (Connection connection = JDBCUtils.getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TODO_BY_ID);) {
			preparedStatement.setLong(1, todoId);
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();

			
			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				String amount = rs.getString("amount");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				boolean isDone = rs.getBoolean("is_done");
				todo = new Todo(id, title, username, description,amount, targetDate, isDone);
			}
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
		return todo;
	}

	@Override
	public List<Todo> selectAllTodos() {

		List<Todo> todos = new ArrayList<>();

		
		try (Connection connection = JDBCUtils.getConnection();

				
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TODOS);) {
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();

			
			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				String amount = rs.getString("amount");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				boolean isDone = rs.getBoolean("is_done");
				todos.add(new Todo(id, title, username, description,amount,targetDate, isDone));
			}
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
		return todos;
	}

	@Override
	public boolean deleteTodo(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_TODO_BY_ID);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	@Override
	public boolean updateTodo(Todo todo) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_TODO);) {
			statement.setString(1, todo.getTitle());
			statement.setString(2, todo.getUsername());
			statement.setString(3, todo.getDescription());
			statement.setString(4, todo.getAmount());
			statement.setDate(5, JDBCUtils.getSQLDate(todo.getTargetDate()));
			statement.setBoolean(6, todo.getStatus());
			statement.setLong(7, todo.getId());
			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
}
