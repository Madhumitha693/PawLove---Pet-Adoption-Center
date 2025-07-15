public class User {
    public String name;
    public String contact;
    public String preferences;
    public String livingArrangements;

    public User(String name, String contact, String preferences, String livingArrangements) {
        this.name = name;
        this.contact = contact;
        this.preferences = preferences;
        this.livingArrangements = livingArrangements;
    }

    @Override
    public String toString() {
        return name + " | " + contact + " | " + preferences + " | " + livingArrangements;
    }
}
