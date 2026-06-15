public class ElectricMove extends Move {
    
    public ElectricMove(String name, int power, int accuracy, int maxPP) {
        super(name, "Electric", power, accuracy, maxPP);
    }
    
    @Override
    public void use(Pokemon user, Pokemon target) {
        super.use(user, target);
        // Electric moves have a chance to paralyze
        if (Math.random() < 0.1) { // 10% chance
            System.out.println(target.getName() + " was paralyzed!");
        }
    }
} 