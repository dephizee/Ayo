package pro.ayo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class AyoGame{
    private int[] turns= {1, 2};
    private int turn;
    private int counter;
    private int spot;
    private int[] playerSeeds={0,0};
    private int[][] ayoArray;
    private int currentI;
    private int currentJ;
    private boolean spreading;
    private int spreadingCount;
    int currentSeeds;
    public AyoGame(){
        turn = 1;
        counter = 0;
        ayoArray = new int[2][6];
        for(int i = 0; i< 2; i++){
            for(int j = 0; j< 6; j++){
                ayoArray[i][j] = 4;
            }
        }
        spreading = false;
        spreadingCount = 0;
        displayBoard();
    }
    public void displayBoard(){
        System.out.println("\n\nScores "+playerSeeds[0]+" : "+ playerSeeds[1]+"...\n\n");
        System.out.println("\n\nPlayer "+turn+"...\n\n");
        System.out.println("  -    -    -  A -    -    -  ");
        System.out.println("  -    -    -    -    -    -  ");
        for(int i = 0; i< 2; i++){
            System.out.printf("| ");
            for(int j = 0; j< 6; j++){
                System.out.printf("%d |  ",ayoArray[i][j]);
            }
            System.out.println();
            System.out.println("  -    -    -    -    -    -  ");
        }
        System.out.println("  -    -    -  B -    -    -  ");
    }
    public int[][] getAyoBoard(){
        return ayoArray;
    }
    public void setAyoBoard(int[][] x){
         ayoArray = x;
    }
    public int[] getCurrentScore(){
        return playerSeeds;
    }
    public void setCurrentScore(int[] x){
        playerSeeds = x;
    }
    public void changeTurn(){
        counter++;
        turn = turns[counter%2];
    }
    public int getTurn(){
        return turn;
    }
    public void setTurn(int i){
        turn=i;;
    }
    public void setCounter(int i){
        counter=i;;
    }
    public int getCounter(){
        return counter;
    }

    public void playSpot(int i){
        spot = i;
        spread(i);
        System.out.println("finishes at : "+ayoArray[currentI][currentJ]+" "+currentI+currentJ);
    }
    public void skip(){
        spreadingCount++;
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void spread(int i){
        int m,n;
        m = turn-1;
        n = i-1;
        currentSeeds=ayoArray[m][n];
        int nextSeeds=4;
        ayoArray[m][n] = 0;
        if(currentSeeds<1){
            System.out.println("Hole is empty");
            changeTurn();
        }else{
            while(nextSeeds > 0){
                ayoArray[m][n]=0;
                while(currentSeeds>0){
                    if(m==0){
                        n--;
                        if(n<0){
                            m = (m+1)%2;
                            n=0;
                        }
                        ayoArray[m][n] = ayoArray[m][n]+1;
                        skip();
                        checkWinWhile(m, n);

                    }else if(m==1){
                        n++;
                        if(n==6){
                            m = (m+1)%2;
                            n=5;
                        }
                        ayoArray[m][n] = ayoArray[m][n]+1;
                        skip();
                        checkWinWhile(m, n);
                    }
                    currentSeeds--;
                }
                currentSeeds = ayoArray[m][n];
                nextSeeds = currentSeeds-1;
            }
            currentI = m;
            currentJ = n;
        }
    }

    public void checkWin(){
        if(turn == 1 && currentI==1){
            for(int i =0; i<6; i++){
                if(ayoArray[1][i]==4 ){
                    playerSeeds[0] += ayoArray[1][i];
                    ayoArray[1][i] = 0;
                }
            }
        }else if(turn == 2 && currentI==0){
            for(int i =0; i<6; i++){
                if(ayoArray[0][i]==4){
                    playerSeeds[1] += ayoArray[0][i];
                    ayoArray[0][i] = 0;
                }
            }
        }
    }
    public void checkWinWhile(int mm, int nn){
        if(turn == 1 && mm==1){
            if(ayoArray[mm][nn]==4 ){
                playerSeeds[1] += ayoArray[mm][nn];
                ayoArray[mm][nn] = 0;
            }
        }else if(turn == 1 && mm==0){
            if(ayoArray[mm][nn]==4){
                playerSeeds[0] += ayoArray[mm][nn];
                ayoArray[mm][nn] = 0;
            }
        }else if(turn == 2 && mm==0){
            if(ayoArray[mm][nn]==4){
                playerSeeds[0] += ayoArray[mm][nn];
                ayoArray[mm][nn] = 0;
            }
        }else if(turn == 2 && mm==1){
            if(ayoArray[mm][nn]==4){
                playerSeeds[1] += ayoArray[mm][nn];
                ayoArray[mm][nn] = 0;
            }
        }
    }
    public void restartBoard(){
        for(int i = 0; i< 2; i++){
            for(int j = 0; j< 6; j++){
                ayoArray[i][j] = 4;
                turn=1;
                counter = 0;
                playerSeeds[0] = 0;
                playerSeeds[1] = 0;

            }
        }
    }

    public boolean getSpreadingState() {
        return spreading;
    }
    public int getSpreadingCount() {
        if(currentSeeds-1 < 0){
            return 0;
        }
        return currentSeeds-1;

    }

    public void setSpreadOff() {
        currentSeeds = 0;
        spreading = false;

    }
    public void setSpreadOn() {
        spreading = true;
    }
}

