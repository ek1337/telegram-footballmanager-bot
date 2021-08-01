package ru.ekimov.telegram_fooball_wh_bot.model;

import javax.persistence.*;

@Entity(name = "Participant")
@Table
public class Participant {


    @Id
    @Column(name = "id")
    private Long id;

    @Column (name = "first_name",
            columnDefinition = "TEXT")
    private String firstName;

    @Column (name = "last_name",
            columnDefinition = "TEXT")
    private String lastName;

    @Column (name = "nick_name",
            columnDefinition = "TEXT")
    private String nickName;

    public Participant(Long id, String firstName, String lastName, String nickName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
    }

    public Participant() {

    }

    public Participant(Long id) {
    this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
