package pro.ayo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by AbdulfataiAbdulhafiz on 1/2/2018.
 */

public class AyoView extends View {
    private AyoGame ayoGame = null;
    private int[][] arrGame = null;
    private boolean gameOn;
    private int n = 0;

    private Integer[] mBackground = { Color.CYAN, Color.GRAY, Color.LTGRAY, Color.MAGENTA, Color.YELLOW, Color.WHITE };
    private Paint[] mForegrounds = { makePaint(Color.BLACK), makePaint(Color.BLUE), makePaint(Color.GREEN), makePaint(Color.RED) };
    Bitmap bitmapBg = BitmapFactory.decodeResource(getResources(),R.drawable.main_background);
    Rect rectSBg = new Rect();
    Rect rectDBg = new Rect();


    private int threadSpot;
    private AyoActivity ayoActivity;



    public AyoView(Context context, AyoGame g) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        gameOn = true;
        threadSpot = 0;
        ayoGame = g;
        ayoActivity = (AyoActivity) context;

    }

    public AyoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectSBg.right = bitmapBg.getWidth();
        rectSBg.bottom = bitmapBg.getHeight();
        rectDBg.set( 0,0,getWidth(),getHeight());
        canvas.drawBitmap(bitmapBg,rectSBg,rectDBg,null);
        float x = getWidth()/8;
        float y = getHeight()/12;
        /*if(ayoGame == null) {
            ayoGame = new AyoGame();
        }*/

        arrGame = ayoGame.getAyoBoard();
        n++;
        drawScoreBoard(canvas, x, y);
        drawAyoBoard(canvas, x,y);

        for (int i = 1;i<7; i++){
            drawSeeds(canvas, x, y, arrGame[0][i-1], i, 5);
            drawSeeds(canvas, x, y, arrGame[1][i-1], i, 9);

            Log.d("TAGGG", "\nonDraw: "+ arrGame[0][i-1]+arrGame[1][i-1]);
        }
        if(this.getVisibility() == View.VISIBLE){
            if(ayoGame.getSpreadingState()){
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                        Log.d("Thread TAG", "invalidating...: " + Thread.currentThread().getName());
                    }
                }, 500);
            }
        }
    }
    public void drawScoreBoard(Canvas canvas, float dx, float dy){
        float xStart = 0.0f;
        float yStart = 0.0f;
        float xEnd = dx*8.0f;
        float yEnd = dy*3.0f;
        Paint ovalPaint = makePaint(getResources().getColor(R.color.score_background_color));
        Paint scorePaint = makePaint(getResources().getColor(R.color.score_background_colori));
        Paint pausePaint = makePaint(getResources().getColor(R.color.pause_background_color));
        canvas.drawRect((int)xStart,(int)yStart, (int)xEnd, (int)yEnd,ovalPaint );
        Path p  = new Path();

        p.addRect(xStart, yStart, dx*2.5f, dy*1.2f, Path.Direction.CCW);
        p.addRect(xStart, yStart, dx*0.5f, dy*1.8f, Path.Direction.CW);



        p.addRect(dx*3, dy*1.2f, dx*3.5f, dy*2.0f, Path.Direction.CCW);
        p.addRect(dx, dy*2.0f, dx*3.5f, dy*3.0f, Path.Direction.CCW);
        p.addRect(xStart, yStart, dx*2.5f, dy*1.2f, Path.Direction.CCW);


        p.addRect(dx*4, yStart, dx*4.5f, dy*1.5f, Path.Direction.CCW);

        canvas.drawPath(p, scorePaint);
        Path pause = new Path();
        pause.addRect(xStart, yStart, xEnd, yStart, Path.Direction.CW);
        pause.addRect(dx*6.5f, dy*0.5f,dx*6.8f, dy*2.5f, Path.Direction.CCW);
        pause.addRect(dx*6.9f, dy*0.5f,dx*7.2f, dy*2.5f, Path.Direction.CCW);
        canvas.drawPath(pause, pausePaint);
        pointCurrentPlayer(canvas, dx, dy);

        drawScoreCount(canvas, dx, dy);

    }

    private void drawScoreCount(Canvas canvas, float dx, float dy) {
        Paint tPaint = new Paint();
        tPaint.setTextSize(dx*0.3f);
        tPaint.setColor(getResources().getColor(R.color.fore_text_color));
        canvas.drawText("Player One",dx*0.2f, dy*1.0f, tPaint);
        canvas.drawText("Player Two",dx*1.4f, dy*2.8f, tPaint);
        canvas.drawText(""+ayoGame.getCurrentScore()[0],dx*1.2f, dy*1.9f, tPaint);
        canvas.drawText(":",dx*1.5f, dy*1.8f, tPaint);
        canvas.drawText(""+ayoGame.getCurrentScore()[1],dx*1.7f, dy*1.9f, tPaint);

        canvas.drawText(""+ayoGame.getSpreadingCount(),dx*4.1f, dy*1.3f, tPaint);
    }

    private void pointCurrentPlayer(Canvas canvas, float dx, float dy) {
        if(ayoGame.getTurn()==1){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.player_one);
            Rect rectS = new Rect();
            Rect rectD = new Rect();
            rectS.right = bitmap.getWidth();
            rectS.bottom = bitmap.getHeight();
            rectD.set( (int)(dx*2.5),(int)(dy*0.2),(int)(dx*3),(int)(dy*0.8));
            canvas.drawBitmap(bitmap,rectS,rectD,null);

        }else if(ayoGame.getTurn()==2){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.player_two);
            Rect rectS = new Rect();
            Rect rectD = new Rect();
            rectS.right = bitmap.getWidth();
            rectS.bottom = bitmap.getHeight();
            rectD.set( (int)(dx*0.4),(int)(dy*2.1),(int)(dx*0.9),(int)(dy*2.9));
            canvas.drawBitmap(bitmap,rectS,rectD,null);

        }

    }

    public void drawAyoBoard(Canvas canvas, float dx, float dy) {
        float xStart = dx*0.5f;
        float yStart = dy*4.0f;
        float xEnd = dx*7.5f;
        float yEnd = dy*7.5f;
        Paint ovalPaint = makePaint(getResources().getColor(R.color.pot_outter));
        canvas.drawRect((int)xStart,(int)yStart, (int)xEnd, (int)yEnd,ovalPaint );
        yStart = dy*8.0f;
        yEnd = dy*11.75f;
        canvas.drawRect((int)xStart,(int)yStart, (int)xEnd, (int)yEnd,ovalPaint );

    }

    private void drawSeeds(Canvas canvas, float viewWidth, float viewHeight, int count, int i, float j) {
        float x = viewWidth;
        float y = viewHeight;
        float radius = (x/6.0f);

        float startX = x*i+radius+radius;
        float startY = y*j-(radius/2);
        float textStartY = (y*j)+y+radius;


        float offset = x/3.0f;
        Path seeds = new Path();
        Paint paint = new Paint();
        Paint forePaint = new Paint();
        forePaint.setColor(getResources().getColor(R.color.fore_color));
        forePaint.setTextSize(x*0.8f);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        switch (count){
            case 0:
                canvas.drawText(""+count,startX,textStartY, forePaint);
                return;
            case 1:
                seeds.addCircle(startX, startY, radius, Path.Direction.CW);
                canvas.drawPath(seeds,paint);
                return;
            case 2:
                seeds.addCircle(startX, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY, radius, Path.Direction.CW);
                canvas.drawPath(seeds,paint);
                return;
            case 3:
                seeds.addCircle(startX, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX, startY+offset, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY, radius, Path.Direction.CW);
                canvas.drawPath(seeds,paint);
                return;
            case 4:
                seeds.addCircle(startX, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX, startY+offset, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY+offset, radius, Path.Direction.CW);
                canvas.drawPath(seeds,paint);
                return;
            case 5:
                seeds.addCircle(startX, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX, startY+offset, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY+offset, radius, Path.Direction.CW);
                seeds.addCircle(startX, startY+offset*2, radius, Path.Direction.CW);
                canvas.drawPath(seeds,paint);
                return;
            case 6:
                seeds.addCircle(startX, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX, startY+offset, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY+offset, radius, Path.Direction.CW);
                seeds.addCircle(startX, startY+offset*2, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY+offset*2, radius, Path.Direction.CW);
                canvas.drawPath(seeds,paint);
                return;
            default:
                seeds.addCircle(startX, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX, startY+offset, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY+offset, radius, Path.Direction.CW);
                seeds.addCircle(startX, startY+offset*2, radius, Path.Direction.CW);
                seeds.addCircle(startX+offset, startY+offset*2, radius, Path.Direction.CW);
                canvas.drawPath(seeds,paint);
                if((""+count).length() > 1){
                    canvas.drawText(""+count,startX-radius-radius,textStartY, forePaint);
                }else {
                    canvas.drawText(""+count,startX,textStartY, forePaint);
                }

                return;

        }

    }

    private void drawRandomRect(Canvas canvas, int viewWidth, int viewHeight, int avgShapeWidth) {
        float left = (float)(Math.random()*viewWidth);
        float top = (float)(Math.random()*viewHeight);
        float width = (float)(Math.random()*avgShapeWidth);
        float right = left + width;
        float bottom = top + width;
        Paint squareColor = mForegrounds[new Random().nextInt(mForegrounds.length)];
        canvas.drawRect(left, top, right, bottom, squareColor);
    }


    private Paint makePaint(int color) {
        Paint p = new Paint();
        p.setColor(color);
        return(p);
    }
    public AyoGame getGame(){
        return ayoGame;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TAG","onTouchEvent: x " + event.getX() +
                ", y "
                + event.getY());


        int turn;
        if(!ayoGame.getSpreadingState()){
            threadSpot = selectPot(event.getX(),event.getY());
            turn = ayoGame.getTurn();
            invalidate();
        }else {
            Toast.makeText(ayoActivity,"Game still spreading", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(threadSpot == -1){
            Toast.makeText(ayoActivity,"Not your turn", Toast.LENGTH_LONG).show();
        }else if(threadSpot == 0){
            Toast.makeText(ayoActivity,"Not Handled area", Toast.LENGTH_SHORT).show();
        }else{
            if(arrGame[turn-1][threadSpot-1]== 0){
                Toast.makeText(ayoActivity,"Pot is empty", Toast.LENGTH_SHORT).show();
            }else{
                ayoGame.setSpreadOn();
                SpreadingThread spreadThreading = new SpreadingThread();
                spreadThreading.start();
            }
        }

        return super.onTouchEvent(event);

    }

    public int selectPot(float x, float y){
        float dx = getWidth()/8;
        float dy = getHeight()/12;
        int turn = ayoGame.getTurn();
        float sX, sY,eX, eY;
        for (int i =1;i<=6; i++){
            sX=dx*i+(0.05f);
            eX=dx*(i+0.95f);
            if(turn==2){
                sY=dy*8.2f;
                eY=dy*11.3f;
            }else if(turn == 1){
                sY=dy*4.2f;
                eY=dy*7.3f;
            }else {
                return -1;
            }

            if(x>sX && x<eX && y>sY && y<eY){
                return i;
            }
        }
        return 0;

    }
    public class SpreadingThread implements Runnable{
        private Thread t;
        public SpreadingThread(){

        }
        public void run(){
            ayoGame.playSpot(threadSpot);
            ayoGame.checkWin();
            ayoGame.changeTurn();
            ayoGame.setSpreadOff();
        }
        public void start(){
            System.out.println("Starting " +  t.currentThread().getName() );
            if (t == null) {
                t = new Thread (this);
                t.start();
            }
            System.out.println("Starting " +  t.currentThread().getName() );
        }
    }
}


