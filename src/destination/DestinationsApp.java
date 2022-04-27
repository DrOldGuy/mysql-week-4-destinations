package destination;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import destination.entity.Member;
import destination.exception.DestinationException;
import destination.service.DestinationService;

/**
 * This is the main app (with the main method). It demonstrates CRUD operations
 * on a single table.
 * 
 * @author Promineo
 *
 */
public class DestinationsApp {

  private Scanner scanner = new Scanner(System.in);
  private DestinationService destinationService = new DestinationService();

  private List<String> menu = List
      .of( // @formatter:off
      "1) Create tables",
      "2) Add a member",
      "3) List all members",
      "4) Modify a member",
      "5) Delete a member"
  ); // @formatter:on

  /**
   * The main method (entry point for Java application).
   * 
   * @param args Unused but required
   */
  public static void main(String[] args) {
    new DestinationsApp().showMenu();
  }

  /**
   * This displays a menu of options, gets the user selection and executes the
   * selection.
   */
  private void showMenu() {
    boolean done = false;

    while (!done) {
      printMenuSelections();

      try {
        int selection = selectMenuItem();

        switch(selection) {
          case -1:
            exitMenu();
            done = true;
            break;

          case 1:
            createTables();
            break;

          case 2:
            createMember();
            break;

          case 3:
            listMembers();
            break;

          case 4:
            modifyMember();
            break;

          case 5:
            deleteMember();
            break;

          default:
            System.out.println("'" + selection + "' is not valid. Try again.");
            break;
        }
      }
      catch (Exception e) {
        System.out.println(e.getMessage() + " Try again.");
      }
    }
  }

  /**
   * List the members and delete the selected member. This does not check to see
   * if the member was actually deleted.
   * 
   * @throws SQLException
   * 
   */
  private void deleteMember() throws SQLException {
    listMembers();

    Integer memberId = getIntInput("Enter the ID of the member to delete");
    destinationService.deleteMember(memberId);
    System.out.println("Member with ID=" + memberId + " deleted.");
  }

  /**
   * List the members and get the ID of the member to modify.
   * 
   * @throws SQLException
   */
  private void modifyMember() throws SQLException {
    listMembers();
    Member member = getMemberDetails(true);

    destinationService.modifyMember(member);

    System.out.println(member.getFirstName() + " " + member.getLastName()
        + " modified successfully.");
  }

  /**
   * List all members.
   * 
   * @throws SQLException
   */
  private void listMembers() throws SQLException {
    System.out.println("Here are the members:");

    destinationService.fetchAllMembers()
        .forEach(member -> System.out.println("   " + member));

    /* The Lambda can be replaced with an enhanced for loop. */
    // for(Member member : destinationService.fetchAllMembers()) {
    // System.out.println(" " + member);
    // }
  }

  /**
   * Create a new member.
   * 
   * @throws SQLException
   */
  private void createMember() throws SQLException {
    Member member = getMemberDetails(false);

    destinationService.createMember(member);

    System.out.println(member.getFirstName() + " " + member.getLastName()
        + " added successfully.");
  }

  /**
   * Get the member details and return a Member object. If the user does not
   * enter a value, the corresponding field will be {@code null} in the Member
   * object.
   * 
   * @param getMemberId If {@code true}, the member ID is collected.
   * @return The Member object with the user input.
   */
  private Member getMemberDetails(boolean getMemberId) {
    Integer memberId = null;

    if(getMemberId) {
      memberId = getIntInput("Enter ID of member to modify");
    }

    String firstName = getStringInput("Enter the first name");
    String lastName = getStringInput("Enter the last name");
    String email = getStringInput("Enter the email address");
    String phoneNumber = getStringInput("Enter the phone number");
    String password = getStringInput("Enter the super-secret password");

    // @formatter:off
    return Member.builder() 
        .memberId(memberId)
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .phoneNumber(phoneNumber)
        .password(password)
        .build(); // @formatter:on
  }

  /**
   * Create (or recreate) the tables. This will delete any data already in the
   * tables.
   */
  private void createTables() {
    destinationService.createTables();
    System.out.println("Tables created!");
  }

  /**
   * Print a message and exit the menu.
   */
  private void exitMenu() {
    System.out.println("\nThanks for using the menu. TTFN!");
  }

  /**
   * Get the user's menu selection.
   * 
   * @return -1 if the user doesn't enter anything. This will cause the menu to
   *         quit. Otherwise, the user's selection is returned.
   */
  private int selectMenuItem() {
    Integer selection = getIntInput("Please enter a menu item (Enter to quit)");

    return Objects.isNull(selection) ? -1 : selection;
  }

  /**
   * Print the menu choices.
   */
  private void printMenuSelections() {
    System.out.println("You can do any of the following:");

    menu.forEach(op -> System.out.println("   " + op));

    /*
     * The Lambda expression can be replaced with an enhanced for loop if
     * desired.
     */
    // for(String op : menu) {
    // System.out.println(" " + op);
    // }
  }

  /**
   * Get the user input and convert it to an integer.
   * 
   * @param prompt The prompt to print.
   * @return An Integer value, or {@code null} if the user didn't enter
   *         anything.
   * @throws DestinationException Thrown if the user does not enter a value that
   *         can be converted to an Integer.
   */
  private Integer getIntInput(String prompt) {
    String value = getStringInput(prompt);

    if(Objects.isNull(value) || value.isBlank()) {
      return null;
    }

    try {
      return Integer.valueOf(value);
    }
    catch (NumberFormatException e) {
      /*
       * The NumberFormatException has a really weird message. Convert it to
       * something more understandable.
       */
      throw new DestinationException("'" + value + "' is not a valid integer.");
    }
  }

  /**
   * Collect the user's input as a String.
   * 
   * @param prompt The prompt to print.
   * @return The input String, or {@code null} if the user didn't enter
   *         anything.
   */
  private String getStringInput(String prompt) {
    System.out.print(prompt + ": ");
    String line = scanner.nextLine();

    return line.isEmpty() ? null : line.trim();
  }
}
