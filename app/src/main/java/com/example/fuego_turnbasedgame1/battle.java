package com.example.fuego_turnbasedgame1;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class battle {
    //Allows the use of code from other classes
    gameScreen gs;
    MonsterStatus mon = new MonsterStatus();
    public battle(gameScreen gs){this.gs = gs;}

    String turnAdvance;
    //the turnAdvance String. Used for calling methods through the switch statement
    Random randomizer = new Random();
    //Randomizer

    private static int turnNumber = 1;
    //String for turn number
    int monsterdps = 0;
    //integer for monsterdps

    int defenseBuff = 0;

    int skill2cd = 0;
    //boolean integers

    //Getters and Setters
    public static int getTurnNumber() {return battle.turnNumber;}
    public int getSkill2cd() {return skill2cd;}
    public int getMonsterdps() {return monsterdps;}
    public void setTurnNumber(int turnNumber) {battle.turnNumber = turnNumber;}
    public void setMonsterdps(int monsterdps) {this.monsterdps = monsterdps;}

    public void turnButton (String position){
        //The Switch statement used to call methods assigned to the string case
        switch(position){
            case "playerTurn":playerTurn(); break;
            case "enemyTurn": enemyTurn(); break;
            case "resetGame": resetGame(); break;
        }
    }

    public void heroSkill1(int maxdmg, int mindmg, String heroName, TextView monText, Button btn1, TextView log){
        //The First Skill Button

        int herodps = randomizer.nextInt((maxdmg - mindmg)) + mindmg;
        //sets the number for "herodps" to the randomization of the hero's max damage and min damage

        //Sets the values that were changed
        mon.setMonHPts(mon.getMonHPts()-herodps);
        monText.setText(String.valueOf(mon.getMonHPts()));
        btn1.setVisibility(View.VISIBLE);
        btn1.setText("Next Turn");
        log.setText("Our Hero  " + String.valueOf(heroName) + " dealt " + herodps + " to the enemy");

        //On pressing the button, disables the button to be pressed
        gs.btnSkill1.setEnabled(false);
        gs.btnSkill2.setEnabled(false);
        gs.btnSkill3.setEnabled(false);

        //Changes the string to call the method assigned to the string through the switch statement
        turnAdvance = "enemyTurn";

        if (mon.getMonHPts() < 0){
            //If else statement in the condition of the monster having 0 health value

            log.setText("Our Hero  " + String.valueOf(heroName) + " dealt " + herodps + " to the enemy and won the battle!");
            btn1.setText("Reset Game");
            turnAdvance = "resetGame";
            setTurnNumber(1);
            gs.setHeroHP(gs.getHeroMaxHP());
            mon.setMonHPts(mon.getMonMaxHPts());
        }
    }

    public void heroSkill2(String heroName, Button btn1, TextView log){
        //The Second Skill Button

        //Sets the value for specific integers upon pressing the button
        defenseBuff = 1;
        skill2cd = 3;
        log.setText("Our Hero  " + String.valueOf(heroName) + " raises his defenses!");
        btn1.setVisibility(View.VISIBLE);
        btn1.setText("Next Turn");
        turnAdvance = "enemyTurn";

        gs.btnSkill1.setEnabled(false);
        gs.btnSkill2.setEnabled(false);
        gs.btnSkill3.setEnabled(false);
    }

    public void playerTurn (){
        //Method for the player turn

        gs.btnNextTurn.setVisibility(View.GONE);
        //Sets the button to be gone until a skill button is pressed

        //Sets the updated values
        gs.txtLog.setText("Hero's turn!");
        setTurnNumber(getTurnNumber()+1);
        gs.txtTurn.setText("Turn " + String.valueOf(battle.getTurnNumber()));

        //Allows the button to be pressed
        gs.btnSkill1.setEnabled(true);
        if (skill2cd > 0){
            //Disables the btn to be pressed when skill cooldown isn't 0
            defenseBuff = 1;
            gs.txtSkill2cd.setVisibility(View.VISIBLE);
            gs.txtSkill2cd.setText(String.valueOf(getSkill2cd()));
            gs.btnSkill2.setEnabled(false);
            skill2cd--;
        }else if (skill2cd==0){
            //Enables the button to be pressed when skill cooldown is 0
            //Also sets the defensbuff integer to 0
            defenseBuff = 0;
            gs.txtSkill2cd.setVisibility(View.INVISIBLE);
            gs.btnSkill2.setEnabled(true);
        }
        gs.btnSkill3.setEnabled(true);
    }

    public void enemyTurn() {
        Random randomizer = new Random();
        if (defenseBuff == 1){
            setMonsterdps((int)((randomizer.nextInt((mon.getMonMaxDmg() - mon.getMonMinDmg())) + mon.getMonMinDmg())*(25.0f/100.0f)));
        }else {
            setMonsterdps(randomizer.nextInt((mon.getMonMaxDmg() - mon.getMonMinDmg())) + mon.getMonMinDmg());
        }
        //sets the number for "monsdps" to the randomization of the monster's max damage and min damage

            //Sets the updated values
            gs.setHeroHP(gs.getHeroHP() - getMonsterdps());
            gs.txtHeroHP.setText(String.valueOf(gs.getHeroHP()));
            gs.btnNextTurn.setText("Next Turn");
            gs.txtLog.setText("The Monster " + String.valueOf(mon.getMonsterName()) + " dealt " + getMonsterdps() + " to you ");

            //Calls the method assigned to the string from the switch statement
            turnAdvance = "playerTurn";

            if (gs.getHeroHP() < 0)
            {
                //If else statement in the condition of the hero having 0 health value
                gs.txtLog.setText("The Monster " + String.valueOf(mon.getMonsterName()) + " dealt " + getMonsterdps() + " to you. YOU LOSE ");
                gs.btnNextTurn.setText("Reset Game");
                turnAdvance = "resetGame";
                gs.setHeroHP(gs.getHeroMaxHP());
                mon.setMonHPts(mon.getMonMaxHPts());
            }
        }

        public void resetGame(){
        //Method for resetting the game to it's default values
            gs.txtLog.setText("Our Hero encountered a monster!");
            turnAdvance = "resetGame";
            setTurnNumber(0);
            skill2cd = 0;
            gs.txtHeroHP.setText(String.valueOf(gs.getHeroMaxHP()));
            gs.txtMonsHP.setText(String.valueOf(mon.getMonHPts()));

            gs.txtTurn.setText("");
            gs.btnNextTurn.setText("Fight!");

            //Calls the method assigned to the string from the switch statement
            turnAdvance = "playerTurn";
        }
}



