public class FireMove extends Move {
    
    public FireMove(String name, int power, int accuracy, int maxPP) {
        super(name, "Fire", power, accuracy, maxPP);
    }
    
    @Override
    public void use(Pokemon user, Pokemon target) {
        super.use(user, target);
        // Fire moves have a chance to burn
        if (Math.random() < 0.1) { // 10% chance
            System.out.println(target.getName() + " was burned!");
        }
    }
} 