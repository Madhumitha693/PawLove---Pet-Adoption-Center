public class Pet {
    protected String name;
    protected String breed;
    protected int age;
    protected boolean isAdopted;
    protected String imagePath;

    public Pet(String name, String breed, int age, String imagePath) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.imagePath = imagePath;
        this.isAdopted = false;
    }

    public void adopt() {
        this.isAdopted = true;
    }

    public String getDetails() {
        return name + " | " + breed + " | Age: " + age + " | " + (isAdopted ? "Adopted" : "Available");
    }

    public String adoptionMessage() {
        return "Thank you for adopting " + name + "!";
    }

    @Override
    public String toString() {
        return name + " - " + breed + " (" + age + " yrs)";
    }

    public String getImagePath() {
        return imagePath;
    }

    public void cancelAdoption() {
    this.isAdopted = false;
}

}
