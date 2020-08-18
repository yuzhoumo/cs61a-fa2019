import java.util.ArrayList;
public class Farm{
    private ArrayList<Animal> animals = new ArrayList<>();

    /** Adds an animal toAdd to the farm. */
    void addAnimal(Animal toAdd){
        animals.add(toAdd);
    }

    /** Takes an index between 0 and animalCount() - 1 (inclusive)
     *  and returns the animal at that index. */
    Animal getAnimal(int index){
        return animals.get(index);
    }
    
    /** Returns the number of animals on the farm. */
    int animalCount(){
        return animals.size();
    }

    public static void main(String[] args){
        Farm farm = new Farm();
        farm.addAnimal(new Dog("Biscuit", 2));
        farm.getAnimal(1);
    }
}
