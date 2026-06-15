import java.util.*;

public abstract class Pokemon {
    protected String name;
    protected String type;
    protected int level;
    protected int maxHP;
    protected int currentHP;
    protected int attack;
    protected int defense;
    protected int speed;
    protected List<Move> moves;
    protected int experience;
    protected int experienceToNextLevel;
    
    public Pokemon(String name, String type, int level, int maxHP, int attack, int defense, int speed) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.moves = new ArrayList<>();
        this.experience = 0;
        this.experienceToNextLevel = level * 100;
        initializeMoves();
    }
    
    protected abstract void initializeMoves();
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getMaxHP() {
        return maxHP;
    }
    
    public int getCurrentHP() {
        return currentHP;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public List<Move> getMoves() {
        return moves;
    }
    
    public boolean isFainted() {
        return currentHP <= 0;
    }
    
    public void takeDamage(int damage) {
        currentHP = Math.max(0, currentHP - damage);
    }
    
    public void heal(int amount) {
        currentHP = Math.min(maxHP, currentHP + amount);
    }
    
    public void gainExperience(int exp) {
        experience += exp;
        while (experience >= experienceToNextLevel) {
            levelUp();
        }
    }
    
    private void levelUp() {
        level++;
        experience -= experienceToNextLevel;
        experienceToNextLevel = level * 100;
        
        // Increase stats
        maxHP += 10;
        attack += 5;
        defense += 5;
        speed += 3;
        
        // Heal to full HP when leveling up
        currentHP = maxHP;
        
        System.out.println(name + " leveled up to level " + level + "!");
    }
    
    public Move selectMove() {
        if (moves.isEmpty()) {
            return null;
        }
        return moves.get((int)(Math.random() * moves.size()));
    }
    
    public void useMove(Move move, Pokemon target) {
        if (move != null && !isFainted()) {
            move.use(this, target);
        }
    }
    
    public void useMoveWithMultiplier(Move move, Pokemon target, double multiplier) {
        if (move != null && !isFainted()) {
            move.useWithMultiplier(this, target, multiplier);
        }
    }
    
    @Override
    public String toString() {
        return name + " (Level " + level + ", " + type + ")";
    }
} 