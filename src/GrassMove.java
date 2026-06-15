public class GrassMove extends Move {
    
    public GrassMove(String name, int power, int accuracy, int maxPP) {
        super(name, "Grass", power, accuracy, maxPP);
    }
    
    @Override
    public void use(Pokemon user, Pokemon target) {
        super.use(user, target);
        // Grass moves have a chance to heal the user
        if (Math.random() < 0.2) { // 20% chance
            int healAmount = user.getMaxHP() / 10;
            user.heal(healAmount);
            System.out.println(user.getName() + " was healed by " + healAmount + " HP!");
        }
    }
} 