package destination.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import destination.entity.Member;
import destination.exception.DestinationException;

/**
 * This class actually talks to the MySQL database. For all the SQL operations,
 * the Connection, PreparedStatement (or Statement) and ResultSet objects are
 * created within a try-with-resources statement. This ensures that the objects
 * are properly closed. You do not have to do this for the homework since it
 * wasn't mentioned in the videos.
 * 
 * @author Promineo
 *
 */
public class DestinationDao {

  /**
   * Execute several SQL statements as a batch.
   * 
   * @param batch The list of SQL statements to execute.
   */
  public void createTables(List<String> batch) {
    try (Connection conn = DbConnection.getConnection()) {
      try (Statement stmt = conn.createStatement()) {
        for(String sql : batch) {
          stmt.addBatch(sql);
        }

        stmt.executeBatch();
      }
    }
    catch (SQLException e) {
      throw new DestinationException(e);
    }
  }

  /**
   * Create a member row from a member object. If there is a {@code null} in one
   * of the columns in the member, MySQL will throw an exception.
   * 
   * @param member The Member object.
   * @throws SQLException
   */
  public void createMember(Member member) throws SQLException {
    String sql = "INSERT INTO members "
        + "(first_name, last_name, email, phone_number, password) " + "VALUES "
        + "(?, ?, ?, ?, ?)";

    try (Connection conn = DbConnection.getConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        /*
         * In the code below, the parameters must not be null. To handle a null,
         * you need to do something like the commented out code.
         */
        // stmt.setNull(1, java.sql.Types.VARCHAR);

        stmt.setString(1, member.getFirstName());
        stmt.setString(2, member.getLastName());
        stmt.setString(3, member.getEmail());
        stmt.setString(4, member.getPhoneNumber());
        stmt.setString(5, member.getPassword());

        stmt.executeUpdate();
      }
    }
  }

  /**
   * Returns all member rows as a list of Member objects.
   * 
   * @return The members
   * @throws SQLException
   */
  public List<Member> fetchAllMembers() throws SQLException {
    String sql = "SELECT * FROM members";

    try (Connection conn = DbConnection.getConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        try (ResultSet rs = stmt.executeQuery()) {
          List<Member> members = new LinkedList<>();

          while (rs.next()) {
            Member member = Member.builder()
                .memberId(rs.getObject("member_id", Integer.class))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .phoneNumber(rs.getString("phone_number"))
                .password(rs.getString("password")).build();

            members.add(member);
          }

          return members;
        }
      }
    }
  }

  /**
   * Modify a member row.
   * 
   * @param member
   * @throws SQLException
   */
  public void modifyMember(Member member) throws SQLException {
    Connection conn = DbConnection.getConnection();
    List<String> params = new LinkedList<>();
    String sql = buildModifySql(member, params);

    /* To see what the SQL looks like, uncomment the line below. */
    // System.out.println("Modify SQL: " + sql);

    PreparedStatement stmt = conn.prepareStatement(sql);

    /*
     * The parameters with modified data are in the params list. We also need to
     * add the member ID, which is in the WHERE clause.
     */
    for(int parmNo = 1; parmNo <= params.size(); parmNo++) {
      stmt.setString(parmNo, params.get(parmNo - 1));
    }

    stmt.setInt(params.size() + 1, member.getMemberId());
    stmt.executeUpdate();
  }

  /**
   * Build the SQL for the modify operation.
   * 
   * @param member The member
   * @param params The parameter array, which takes the non-null values.
   * @return The parameter values in the params list. Returns the SQL as the
   *         method return value.
   */
  private String buildModifySql(Member member, List<String> params) {
    StringBuilder sql = new StringBuilder();

    /*
     * This creates a String like: "first_name = ?, last_name = ?". If a value
     * is null it is not included. If all values are null, MySQL will throw an
     * invalid SQL exception.
     */
    addIfNotNull(member.getFirstName(), "first_name", params, sql);
    addIfNotNull(member.getLastName(), "last_name", params, sql);
    addIfNotNull(member.getEmail(), "email", params, sql);
    addIfNotNull(member.getPhoneNumber(), "phone_number", params, sql);
    addIfNotNull(member.getPassword(), "password", params, sql);

    /* Add the UPDATE part of the SQL at the start of the String. */
    sql.insert(0, "UPDATE members SET ");

    /* Add the WHERE clause. */
    sql.append(" WHERE member_id = ?");

    return sql.toString();
  }

  /**
   * Add a value to the StringBuilder and to the List if the value is not null.
   * 
   * @param columnName The name of the column to add.
   * @param value The value of the column.
   * @param params The list onto which the value is added.
   * @param sql The StringBuilder that is modified and returned.
   */
  private void addIfNotNull(String value, String columnName,
      List<String> params, StringBuilder sql) {

    if(Objects.nonNull(value) && !value.isBlank()) {
      if(!sql.isEmpty()) {
        sql.append(", ");
      }

      sql.append(columnName).append(" = ?");
      params.add(value);
    }
  }

  /**
   * Delete a member row given the member ID. This does not throw an exception
   * if the member ID is invalid.
   * 
   * @param memberId The member ID.
   */
  public void deleteMember(Integer memberId) {
    String sql = "DELETE FROM members WHERE member_id = ?";

    try (Connection conn = DbConnection.getConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, memberId);
        stmt.executeUpdate();
      }
    }
    catch (SQLException e) {
      throw new DestinationException(e);
    }
  }
}
