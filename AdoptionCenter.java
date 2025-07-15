import java.util.ArrayList;

public class AdoptionCenter {
    private ArrayList<Pet> pets = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Application> applications = new ArrayList<>();

    public AdoptionCenter() {
        pets.add(new Dog("Buddy", "Labrador", 3, "/images/dog1.jpg"));
        pets.add(new Dog("Rocky", "Beagle", 2, "/images/dog2.jpg"));
        pets.add(new Cat("Whiskers", "Siamese", 1, "/images/cat1.jpg"));
        pets.add(new Cat("Luna", "Persian", 4, "/images/cat2.jpg"));
        pets.add(new Bird("Tweety", "Parakeet", 1, "/images/bird1.jpg"));
        pets.add(new Bird("Sky", "Macaw", 5, "/images/bird2.jpg"));
    }

    public ArrayList<Pet> getAvailablePets() {
        ArrayList<Pet> available = new ArrayList<>();
        for (Pet p : pets) {
            if (!p.isAdopted) {
                available.add(p);
            }
        }
        return available;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public void applyForAdoption(User user, Pet pet) {
        pet.adopt();
        applications.add(new Application(user, pet));
    }
}
