package pl.edu.pk.ztpai_projekt.dto;

public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;

    public CustomerResponse(Long id, String firstName, String lastName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}