public class Application {
    private User user;
    private Pet pet;

    public Application(User user, Pet pet) {
        this.user = user;
        this.pet = pet;
    }

    public User getUser() {
        return user;
    }

    public Pet getPet() {
        return pet;
    }

    public String getSummary() {
        return user.name + " adopted " + pet.name;
    }
}
