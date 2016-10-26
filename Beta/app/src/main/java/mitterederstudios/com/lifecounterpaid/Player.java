package mitterederstudios.com.lifecounterpaid;

import java.util.ArrayList;

/**
 * Created by Mac on 12/11/2014.
 */
public class Player {

    int life;
    String name;
    ArrayList<Tokens> TokenStack;

    public Player(){
        life = 20;
        name = "";
        TokenStack = new ArrayList<Tokens>();
    }
    public Player(String name){
        life = 20;
        this.name = name;
        TokenStack = new ArrayList<Tokens>();
    }
    public Player(int life){
        this.life = life;
    }
    public int getLife(){
        return life;
    }
    public void changeName(String newName){
        name = newName;
    }
    public String getName(){
        return name;
    }
    public void addLife(int passedVar){
        life += passedVar;
    }
    public void addToken(int attack, int defense) {
        TokenStack.add(new Tokens(attack, defense));
    }
    public ArrayList<Tokens> getTokens(){
        return TokenStack;
    }
    public void deleteToken(int pos){
        TokenStack.remove(pos);
    }
    public void editToken(int pos, int attack, int defense){
        TokenStack.get(pos).edit(attack, defense);
    }
}
