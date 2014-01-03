package by.epam.library.model;


public class Admin {
    private String name;
    private String surname;
    private int age;
    private LoginInfo loginInfo;

    public Admin() {
    }

    public int getAge() {
        return age;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Admin{" + "name = " + name + " surname = " + surname + " age = " + age + " loginInfo = " + loginInfo + '}';
    }

}
