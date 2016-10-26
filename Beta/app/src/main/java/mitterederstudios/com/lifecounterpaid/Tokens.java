package mitterederstudios.com.lifecounterpaid;

/**
 * Created by Mac on 12/11/2014.
 */
public class Tokens {

    int attack, defense;

    public Tokens(){
        attack = 0;
        defense = 0;
    }
    public Tokens(int attack, int defense){
        this.attack = attack;
        this.defense = defense;
    }
    public void set(int attack, int defense){
        this.attack = attack;
        this.defense = defense;
    }
    public void edit(int attack, int defense){
        this.attack += attack;
        this.defense += defense;
    }
    public int getAttack(){
        return attack;
    }
    public int getDefense(){
        return defense;
    }
}
