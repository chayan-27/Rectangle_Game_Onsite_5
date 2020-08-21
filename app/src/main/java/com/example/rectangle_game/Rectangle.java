package com.example.rectangle_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Rectangle extends View {

    Rect rect;
    boolean freeze;
    int centerx;
    int centery;
    int prevleft;
    int prevtop;
    int prevright;
    int prevbottom;


    Paint paint;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public Rectangle(Context context) {
        super(context);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     *
     * <p>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public Rectangle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of View allows subclasses to use their
     * own base style when they are inflating. For example, a Button class's
     * constructor would call this version of the super class constructor and
     * supply <code>R.attr.buttonStyle</code> for <var>defStyleAttr</var>; this
     * allows the theme's button style to modify all of the base view attributes
     * (in particular its background) as well as the Button class's attributes.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     */
    public Rectangle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute or style resource. This constructor of View allows
     * subclasses to use their own base style when they are inflating.
     * <p>
     * When determining the final value of a particular attribute, there are
     * four inputs that come into play:
     * <ol>
     * <li>Any attribute values in the given AttributeSet.
     * <li>The style resource specified in the AttributeSet (named "style").
     * <li>The default style specified by <var>defStyleAttr</var>.
     * <li>The default style specified by <var>defStyleRes</var>.
     * <li>The base values in this theme.
     * </ol>
     * <p>
     * Each of these inputs is considered in-order, with the first listed taking
     * precedence over the following ones. In other words, if in the
     * AttributeSet you have supplied <code>&lt;Button * textColor="#ff000000"&gt;</code>
     * , then the button's text will <em>always</em> be black, regardless of
     * what is specified in any of the styles.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @param defStyleRes  A resource identifier of a style resource that
     *                     supplies default values for the view, used only if
     *                     defStyleAttr is 0 or can not be found in the theme. Can be 0
     *                     to not look for defaults.
     */
    public Rectangle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {

        if (rect == null || paint == null) {
            initialize();
        }


        canvas.drawRect(rect, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float pointX = event.getX();
        float pointY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                Log.d("down_touch", rect.top + " : : " + (int) pointY);


                if (rect.left < pointX && rect.right > pointX && rect.top < pointY && rect.bottom > pointY) {
                    freeze = true;
                    Log.d("inside_touch", "yes");

                } else {
                    Log.d("inside_touch", "no");

                    freeze = false;
                }


                return true;

            case MotionEvent.ACTION_MOVE:
                if (freeze) {
                     centerx=rect.centerX();
                     centery=rect.centerY();
                     prevleft=rect.left;
                     prevtop=rect.top;
                     prevright=rect.right;
                     prevbottom=rect.bottom;


                    rect.left = (int) pointX-(Math.abs(centerx-prevleft)) ;
                    rect.top = (int) pointY-(Math.abs(centery-prevtop));
                    rect.right = rect.left + 2*(Math.abs(centerx-prevleft));
                    rect.bottom = rect.top + 2*(Math.abs(centery-prevtop));
                }else{
                    if( rect.left < pointX && rect.right > pointX && Math.abs(rect.top-pointY)<=100){
                        rect.top= (int) pointY;
                    }else if(rect.left < pointX && rect.right > pointX && Math.abs(rect.bottom-pointY)<=100){
                        rect.bottom= (int) pointY;
                    }else if(rect.top < pointY && rect.bottom > pointY && Math.abs(rect.left-pointX)<=100){
                        rect.left= (int) pointX;
                    }else if(rect.top < pointY && rect.bottom > pointY && Math.abs(rect.right-pointX)<=100){
                        rect.right= (int) pointX;
                    }
                }


                break;

            default:
                return false;
        }

        postInvalidate();
        return false;


    }

    public void initialize() {
        rect = new Rect();
        paint = new Paint();
        rect.left = getWidth()/2-200;
        rect.top = getHeight()/2-200;
        rect.right = rect.left + 300;
        rect.bottom = rect.top + 400;
        paint.setColor(Color.YELLOW);
        Log.d("initialize","yes");
    }

}
