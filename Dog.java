public class Dog extends Pet {
    public Dog(String name, String breed, int age, String imagePath) {
        super(name, breed, age, imagePath);
    }

    @Override
    public String adoptionMessage() {
        return "Woof! I'm " + name + ", your loyal dog!";
    }
}
