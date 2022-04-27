package destination.entity;

import javax.annotation.processing.Generated;

/**
 * This holds the data for a member row. This is an immutable object in that
 * once created, the data cannot be changed. There are Getters for each field
 * but no Setters. The object must be created using the {@link Builder}.
 * <p>
 * This is used like follows. Note that you don't need to call all the setter
 * methods.
 * 
 * <pre>
 * <code>
 * Member member = Member.builder()
 *     .memberId(memberId)
 *     .firstName(firstName)
 *     .lastName(lastName)
 *     .email(email)
 *     .phoneNumber(phoneNumber)
 *     .password(password)
 *     .build();
 * </code>
 * </pre>
 * 
 * @author Promineo
 *
 */
public class Member {
  private Integer memberId;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String password;

  /**
   * This constructor is private and is called by the Builder (internal class).
   * It was created by Eclipse (Source menu, "Generate constructor using
   * fields...").
   * 
   * @param memberId The member ID
   * @param firstName The member first name
   * @param lastName The member last name
   * @param email The member email address
   * @param phoneNumber The member phone number
   * @param password The member password
   */
  @Generated(date = "2022-04-26T18:30", value = {"Eclipse"})
  private Member(Integer memberId, String firstName, String lastName,
      String email, String phoneNumber, String password) {
    super();
    this.memberId = memberId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }

  /**
   * The Getters and Setters were generated by Eclipse automatically. (Source
   * menu, "Generate Getters and Setters", select all, generate.)
   * 
   * @return
   */
  @Generated(date = "2022-04-26T18:30", value = {"Eclipse"})
  public Integer getMemberId() {
    return memberId;
  }

  @Generated(date = "2022-04-26T18:30", value = {"Eclipse"})
  public String getFirstName() {
    return firstName;
  }

  @Generated(date = "2022-04-26T18:30", value = {"Eclipse"})
  public String getLastName() {
    return lastName;
  }

  @Generated(date = "2022-04-26T18:30", value = {"Eclipse"})
  public String getEmail() {
    return email;
  }

  @Generated(date = "2022-04-26T18:30", value = {"Eclipse"})
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Generated(date = "2022-04-26T18:30", value = {"Eclipse"})
  public String getPassword() {
    return password;
  }

  /**
   * This was generated automatically by Eclipse. (Source menu, "Generate
   * toString()...")
   */
  @Override
  @Generated(date = "2022-04-26T18:30", value = {"Eclipse"})
  public String toString() {
    return "Member [memberId=" + memberId + ", firstName=" + firstName
        + ", lastName=" + lastName + ", email=" + email + ", phoneNumber="
        + phoneNumber + ", password=" + password + "]";
  }

  /**
   * Implement the Builder Design Pattern. The builder() method returns a
   * Builder inner class. The Builder class has methods that allow the caller to
   * add values to the Member object.
   * 
   * @return
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * This is the Builder inner class. All the setter methods are named the same
   * as the class attributes. Each setter returns the Builder object so they can
   * be chained together. The {@link #build()} method calls the Member
   * constructor and returns the Member object.
   * 
   * @author Promineo
   *
   */
  public static class Builder {
    private Integer memberId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

    /**
     * Call this to create and return the Member object.
     * 
     * @return The Member object.
     */
    public Member build() {
      return new Member(memberId, firstName, lastName, email, phoneNumber,
          password);
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder memberId(Integer memberId) {
      this.memberId = memberId;
      return this;
    }
  }
}
