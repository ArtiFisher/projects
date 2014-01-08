package by.epam.library.beans;


public class Librarian {
    private String name;
    private String surname;
    private EntryData entryData;

    public Librarian() {
    }

    public EntryData getEntryData() {
        return entryData;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setEntryData(EntryData entryData) {
        this.entryData = entryData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Librarian{" + "name = " + name + " surname = " + surname + " entrydata = " + entryData + '}';
    }

}
