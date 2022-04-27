package destination.service;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;
import destination.DestinationsApp;
import destination.dao.DestinationDao;
import destination.entity.Member;
import destination.exception.DestinationException;

/**
 * This class acts as an intermediary between the input/output layer (class
 * {@link DestinationsApp}) and the data layer (class {@link DestinationDao}).
 * You don't need this class for the homework.
 * 
 * @author Promineo
 *
 */
public class DestinationService {

  private DestinationDao destinationDao = new DestinationDao();

  private static final String DESTINATION_SCHEMA = "destination_schema.sql";

  /**
   * Read destination_schema.sql and load it as batch SQL statements. This
   * creates (or recreates) the tables.
   */
  public void createTables() {
    List<String> batch = loadFromFile(DESTINATION_SCHEMA);
    destinationDao.createTables(batch);
  }

  /**
   * This method loads the given file from the classpath and converts it to a
   * list of SQL statements.
   * 
   * @param fileName The file to read.
   * @return A list of SQL statements.
   */
  private List<String> loadFromFile(String fileName) {
    try {
      /*
       * This creates a URI object from the given file name. The class loader is
       * used to find the file on the classpath. The file is in the src
       * directory for this Java project. Eclipse automatically copies files in
       * src to the bin directory when the file is saved. The bin directory is
       * automatically added to the classpath when the application is run.
       */
      URI uri =
          getClass().getClassLoader().getResource(DESTINATION_SCHEMA).toURI();

      /*
       * The Files.readString() method reads the entire contents of a file into
       * a String. Files.readString() takes a java.nio.file.Path object, which
       * is created from the URI object obtained above.
       */
      String content = Files.readString(Paths.get(uri));

      /*
       * Fix the content. Replace all line endings (cr/lf for Windows, lf for
       * MacOS) with spaces. Then replace all multiple spaces with single
       * spaces. Then replace all "( " with "(". This isn't necessary for the
       * SQL parser but it makes the SQL strings prettier.
       */
      content = content.replace("\r\n", " ").replace("\n", " ")
          .replaceAll(" +", " ").replace("( ", "(");

      String[] lines = content.split(";");

      /*
       * This turns the array of String into a Stream of String. Then, each line
       * is trimmed (the String::trim method reference). Then, it is converted
       * to a list and returned.
       */
      return Stream.of(lines).map(String::trim).toList();
    }
    catch (Exception e) {
      throw new DestinationException(e);
    }
  }

  /**
   * Just pass the create member request through to the DAO.
   * 
   * @param member The member to create.
   * @throws SQLException
   */
  public void createMember(Member member) throws SQLException {
    destinationDao.createMember(member);
  }

  /**
   * Pass the fetch all members request through to the DAO.
   * 
   * @return The list of members.
   * @throws SQLException
   */
  public List<Member> fetchAllMembers() throws SQLException {
    return destinationDao.fetchAllMembers();
  }

  /**
   * Update the member with new data. If an attribute in the Member object is
   * {@code null}, the value is not updated.
   * 
   * @param member
   * @throws SQLException
   */
  public void modifyMember(Member member) throws SQLException {
    destinationDao.modifyMember(member);
  }

  /**
   * Delete the member with the given ID.
   * 
   * @param memberId
   */
  public void deleteMember(Integer memberId) {
    destinationDao.deleteMember(memberId);
  }

}
