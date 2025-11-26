public class Player 
{
    public static void main(String[] args)
    {
        
    }
    static void Damage(int damageTaken, int health, int baseDefense)
    {
        damageTaken -= baseDefense;
        health -= damageTaken;
    }
    static void Buff(int baseDamage, int damageBuff)
    {
        baseDamage += damageBuff;
    }
}
