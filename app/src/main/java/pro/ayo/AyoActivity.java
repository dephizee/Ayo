package pro.ayo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by AbdulfataiAbdulhafiz on 12/30/2017.
 */

public class AyoActivity extends AppCompatActivity{

    private int gameType = 0;
    private AyoView ayoView;
    private AyoGame ayoGame = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameType = getIntent().getIntExtra("gameType", 0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(ayoGame == null) {
            ayoGame = new AyoGame();
            MainActivity.ayoGameList.add(ayoGame);
        }
        ayoView = new AyoView(this, ayoGame);
        setContentView(ayoView);
    }
}

