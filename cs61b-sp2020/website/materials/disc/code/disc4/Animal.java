public class Animal {
    protected String name, noise;
    protected int age;
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
        this.noise = "Huh?";
    }
    public String makeNoise() {
        if (age < 2) {
            return noise.toUpperCase();
        }
        return noise;
    }
    public String greet() {
        return name + ": " + makeNoise();
    }
    public static void main(String[] args) {
        /* Cat nyan = new Animal("Nyan Cat", 5); */

        Animal a = new Cat("Olivia Benson", 3);
        a = new Dog("Fido", 7);
        System.out.println(a.greet());
        /* a.playFetch(); */

        /* Dog d1 = a; */
        Dog d2 = (Dog) a;
        d2.playFetch();
        /* (Dog) a.playFetch(); */

        Animal imposter = new Cat("Pedro", 12);
        /* Dog fakeDog = (Dog) imposter; */

        Cat failImposter = new Cat("Jimmy", 21);
        /* Dog failDog = (Dog) failImposter; */
    }
}

class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
        this.noise = "Meow!";
    }
}

class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age);
        noise = "Woof!";
    }
    public void playFetch() {
        System.out.println("Fetch, " + name + "!");
    }
}
