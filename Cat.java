public class Cat extends Pet {
    public Cat(String name, String breed, int age, String imagePath) {
        super(name, breed, age, imagePath);
    }

    @Override
    public String adoptionMessage() {
        return "Meow! I'm " + name + ", your affectionate new cat!";
    }
}
