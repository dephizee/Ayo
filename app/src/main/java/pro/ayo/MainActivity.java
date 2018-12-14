package pro.ayo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<AyoGame> ayoGameList = new ArrayList<>();
    private String[] newGameArray;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        newGameArray = new String[2];
        newGameArray[0]= "Single Player";
        newGameArray[1]= "Multi Player";
    }

    public void StartNewGame(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Select Game")
                .setItems(newGameArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startGame(i);
                    }
                }).show();
    }
    public void startGame(int i){
        Intent intent = new Intent(this, AyoActivity.class);
        intent.putExtra("gameType", i);
        startActivity(intent);
    }

    public void quitGame(View view) {
        finish();
    }

    public void openActiveGames(View view) {
        Intent intent = new Intent(this, ActiveGameActivity.class);
        startActivity(intent);
    }
}
