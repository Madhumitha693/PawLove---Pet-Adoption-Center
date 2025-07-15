public class Bird extends Pet {
    public Bird(String name, String breed, int age, String imagePath) {
        super(name, breed, age, imagePath);
    }

    @Override
    public String adoptionMessage() {
        return "Tweet tweet! I'm " + name + ", your chirpy new bird!";
    }
}
