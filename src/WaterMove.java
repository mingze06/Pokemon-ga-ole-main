public class WaterMove extends Move {
    
    public WaterMove(String name, int power, int accuracy, int maxPP) {
        super(name, "Water", power, accuracy, maxPP);
    }
    
    @Override
    public void use(Pokemon user, Pokemon target) {
        super.use(user, target);
        // Water moves have a chance to lower attack
        if (Math.random() < 0.15) { // 15% chance
            System.out.println(target.getName() + "'s attack was lowered!");
        }
    }
} 