public abstract class Move {
    protected String name;
    protected String type;
    protected int power;
    protected int accuracy;
    protected int maxPP;
    protected int currentPP;
    
    public Move(String name, String type, int power, int accuracy, int maxPP) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.maxPP = maxPP;
        this.currentPP = maxPP;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public int getPower() {
        return power;
    }
    
    public int getAccuracy() {
        return accuracy;
    }
    
    public int getCurrentPP() {
        return currentPP;
    }
    
    public int getMaxPP() {
        return maxPP;
    }
    
    public boolean hasPP() {
        return currentPP > 0;
    }
    
    public void use(Pokemon user, Pokemon target) {
        if (!hasPP()) {
            System.out.println(user.getName() + " tried to use " + name + " but it has no PP left!");
            return;
        }
        
        if (Math.random() * 100 > accuracy) {
            System.out.println(user.getName() + " used " + name + " but it missed!");
            currentPP--;
            return;
        }
        
        currentPP--;
        System.out.println(user.getName() + " used " + name + "!");
        
        int damage = calculateDamage(user, target);
        target.takeDamage(damage);
        
        System.out.println(target.getName() + " took " + damage + " damage!");
        
        if (target.isFainted()) {
            System.out.println(target.getName() + " fainted!");
        }
    }
    
    public void useWithMultiplier(Pokemon user, Pokemon target, double multiplier) {
        if (!hasPP()) {
            System.out.println(user.getName() + " tried to use " + name + " but it has no PP left!");
            return;
        }
        if (Math.random() * 100 > accuracy) {
            System.out.println(user.getName() + " used " + name + " but it missed!");
            currentPP--;
            return;
        }
        currentPP--;
        System.out.println(user.getName() + " used " + name + "!");
        int damage = (int)(calculateDamage(user, target) * multiplier);
        target.takeDamage(damage);
        System.out.println(target.getName() + " took " + damage + " damage!");
        if (target.isFainted()) {
            System.out.println(target.getName() + " fainted!");
        }
    }
    
    protected int calculateDamage(Pokemon user, Pokemon target) {
        double typeEffectiveness = getTypeEffectiveness(user.getType(), target.getType());
        int damage = (int)((power * user.getAttack() / target.getDefense()) * typeEffectiveness);
        return Math.max(1, damage);
    }
    
    protected double getTypeEffectiveness(String attackerType, String defenderType) {
        // Simple type effectiveness system
        if (attackerType.equals("Fire") && defenderType.equals("Grass")) return 2.0;
        if (attackerType.equals("Water") && defenderType.equals("Fire")) return 2.0;
        if (attackerType.equals("Grass") && defenderType.equals("Water")) return 2.0;
        if (attackerType.equals("Electric") && defenderType.equals("Water")) return 2.0;
        if (attackerType.equals("Fire") && defenderType.equals("Water")) return 0.5;
        if (attackerType.equals("Water") && defenderType.equals("Grass")) return 0.5;
        if (attackerType.equals("Grass") && defenderType.equals("Fire")) return 0.5;
        if (attackerType.equals("Electric") && defenderType.equals("Grass")) return 0.5;
        return 1.0;
    }
    
    public void restorePP() {
        currentPP = maxPP;
    }
    
    @Override
    public String toString() {
        return name + " (" + type + ", Power: " + power + ", PP: " + currentPP + "/" + maxPP + ")";
    }
} 