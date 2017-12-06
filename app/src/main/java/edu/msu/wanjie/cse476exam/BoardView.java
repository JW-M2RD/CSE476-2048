package edu.msu.wanjie.cse476exam;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * TODO: document your custom view class.
 */
public class BoardView extends View {

    //To store canvas's width and height
    private int width;
    private int height;

    private int boardSize;
    private int marginX = 0;
    private int marginY = 0;

    private Paint outlinePaint;
    private Paint boardPaint;
    private Paint fillPaint;

    private gameplay gameplay;
    private  MainActivity mt;

    /**
     * Percentage of the display width or height that
     * is occupied by the puzzle.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    public BoardView(Context context) {
        super(context);
        init(null, 0);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStyle(Paint.Style.STROKE);

        boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boardPaint.setColor(0xffcccccc);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(Color.RED);

        gameplay = new gameplay();
        mt = (MainActivity) getContext();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();

        int minDimension = width < height ? width : height;

        boardSize = (int)(minDimension*SCALE_IN_VIEW);

        marginX = (width - boardSize) / 2;
        marginY = (height - boardSize) / 2;

        canvas.drawRect(marginX, marginY, marginX + boardSize, marginY + boardSize, boardPaint);

        int tiles [][] = gameplay.getTiles();

        for (int x = 0; x<tiles.length; x++) {
            for (int y = 0; y<tiles[x].length; y++) {
                if (tiles[x][y] != 0) {

//                    String stringNum = tiles[x][y];
                    String stringNum = ""+tiles[x][y];

                    canvas.save();

                    // Convert x,y to pixels and add the margin, then draw
                    canvas.translate(marginX + x * boardSize/4, marginY + y * boardSize/4);


                    // Scale it to the right size
                    //canvas.scale((boardSize/4), (boardSize/4));

                    // Draw the bitmap
//                    canvas.drawRect(0,0,1,1,fillPaint);


                    Rect targetRect = new Rect(0, 0, boardSize/4, boardSize/4);
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    //paint.setStrokeWidth(3);
                    paint.setTextSize(50);
                    int base = 0xff00068f;
                    int test = tiles[x][y];
                    if (test>2048)
                        test = 2048;
                    paint.setColor(base+test*2000);
                    canvas.drawRect(targetRect, paint);
                    paint.setColor(Color.RED);
                    Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();

                    int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top)/2;
                    // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(stringNum, targetRect.centerX(), baseline, paint);

                    canvas.restore();
                }
            }
        }
    }

    public boolean onTouchEvent(int g) {
        switch (g){
            case 1:
                boolean flag1 = false;
                if (gameplay.flushUp()) {
                    flag1 = true;
                }
                if (gameplay.addUp()) {
                    flag1 = true;
                    gameplay.flushUp();
                }
                if (flag1) {
                    gameplay.generator();
                }
                if (!gameplay.checkLose()) {
                    Toast.makeText(getContext(), "There are no moves, you've lost the game!", Toast.LENGTH_SHORT).show();
                }
                mt.setScore();
                invalidate();
                return true;
            case 2:
                boolean flag2 = false;
                if (gameplay.flushRight()) {
                    flag2 = true;
                }
                if (gameplay.addRight()) {
                    flag2 = true;
                }
                if (flag2) {
                    gameplay.generator();
                }
                if (!gameplay.checkLose()) {
                    Toast.makeText(getContext(), "There are no moves, you've lost the game!", Toast.LENGTH_SHORT).show();
                }
                mt.setScore();
                invalidate();
                return true;
            case 3:
                boolean flag3 = false;
                if (gameplay.flushDown()) {
                    flag3 = true;
                }
                if (gameplay.addDown()) {
                    flag3 = true;
                }
                if (flag3) {
                    gameplay.generator();
                }
                if (!gameplay.checkLose()) {
                    Toast.makeText(getContext(), "There are no moves, you've lost the game!", Toast.LENGTH_SHORT).show();
                }
                mt.setScore();
                invalidate();
                return true;
            case 4:
                boolean flag4 = false;
                if (gameplay.flushLeft()) {
                    flag4 = true;
                }
                if (gameplay.addLeft()) {
                    flag4 = true;
                }
                if (flag4) {
                    gameplay.generator();
                }

                if (!gameplay.checkLose()) {
                    Toast.makeText(getContext(), "There are no moves, you've lost the game!", Toast.LENGTH_SHORT).show();
                }
                mt.setScore();
                invalidate();
                return true;
        }
        return false;
    }

    public String getScore() {
        return ""+gameplay.getScore();
    }

}
