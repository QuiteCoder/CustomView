package com.hpf.customview.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;


public class DashBoardProgressBar extends View {

    private Paint mPaintCursor;
    private Paint mPaint;
    private TextPaint mTextPaint;
    private Paint mPaintShortScale;
    private Paint mPaintLongScale;
    private Path scalePath = new Path();
    private Path scalePath2 = new Path();
    private PathDashPathEffect mPathDashPathEffect;
    private PathDashPathEffect mPathDashPathEffect2;
    private float mLength;
    private final int GRID_COUNT = 9;
    private final String TAG = "DashBoardProgressBar";
    private int mHeight;
    private int mWidth;
    private float mCursorStartX;
    private boolean mInitDone;
    private final float mShortScaleHeight = 15f;
    private final float mLongScaleHeight = 30f;
    private final float mCursorHeight = 50f;
    private float mCurrentOffset;
    private final float mCursorStartOffsetX = 2f;
    private float mLongScaleY;
    private float mCursorScaleStartY;
    private float mCursorScaleStopY;
    private float mCursorRealX;
    private float mDownX;
    private float mAadvance;
    private boolean mHadMoved;
    private float mCursorLimitStartX;
    private float mCursorLimitEndX;

    private final String[] mTexts = {"4", "60", "300", "1800"};
    private float mLongAadvance;
    private int mNewIndex;

    public OnIndexChangeListener mOnIndexChangeListener;
    private int mTrackingPointerId;


    public interface OnIndexChangeListener {
        void onIndexChange(int index);
    }
    public void setOnIndexChangeListener(OnIndexChangeListener onIndexChangeListener) {
        mOnIndexChangeListener = onIndexChangeListener;
    }

    public DashBoardProgressBar(Context context) {
        super(context);
    }

    public DashBoardProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DashBoardProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHeight = getHeight();
        mWidth = getWidth();
        mLength = mWidth * 4/5f;
        mCursorStartX = (mWidth - mLength) /2;
        mCursorLimitStartX = mCursorStartX + mCursorStartOffsetX;
        mCursorLimitEndX = mCursorStartX + mCursorStartOffsetX + mLength;
        Log.d(TAG, "[onLayout] mLength = " + mLength + " mHeight = " + mHeight);
        init();
    }

    private void init() {
        if (mInitDone) return;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(1f);
        mPaint.setColor(Color.parseColor("#FECB00"));
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));
        mTextPaint.setTextSize(20);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mPaintCursor = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCursor.setStrokeWidth(7f);
        mPaintCursor.setColor(Color.parseColor("#FECB00"));
        mPaintCursor.setStyle(Paint.Style.STROKE);

        mPaintShortScale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintShortScale.setColor(Color.parseColor("#ffffff"));
        mPaintShortScale.setStyle(Paint.Style.STROKE);

        mPaintLongScale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLongScale.setColor(Color.parseColor("#ffffff"));
        mPaintLongScale.setStyle(Paint.Style.STROKE);

        //这是刻度的形状,Path.Direction.CCW是顺时针绘制
        scalePath.addRect(0f, 0f, 3f, mShortScaleHeight, Path.Direction.CCW);
        //shape是一个形状，phase是path的偏移量,advance是图形之间的间距
        mAadvance = mLength / GRID_COUNT;
        mLongAadvance = mLength / GRID_COUNT * 3;
        mPathDashPathEffect = new PathDashPathEffect(
                scalePath,
                mAadvance,
                0f,
                PathDashPathEffect.Style.TRANSLATE
        );
        scalePath2.addRect(0f, 0f, 5f, mLongScaleHeight, Path.Direction.CCW);
        mPathDashPathEffect2 = new PathDashPathEffect(
                scalePath2,
                mLongAadvance,
                0f,
                PathDashPathEffect.Style.TRANSLATE
        );
        mInitDone = true;
        mPaintShortScale.setPathEffect(mPathDashPathEffect);
        mPaintLongScale.setPathEffect(mPathDashPathEffect2);
        mLongScaleY = mHeight/2f - (mLongScaleHeight - mShortScaleHeight)/2;
        mCursorScaleStartY = mHeight/2f - (mCursorHeight - mShortScaleHeight)/2;
        mCursorScaleStopY = mHeight/2f + (mCursorHeight + mShortScaleHeight)/2;
        Log.d(TAG, "HPF[init] mNewIndex = " + mNewIndex);
        mCursorRealX = mNewIndex * mAadvance + mCursorLimitStartX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.d(TAG, "[onDraw] mLength = " + mLength);
        //short scale
        canvas.drawLine(mCursorStartX, mHeight/2f, mCursorStartX + mLength, mHeight/2f, mPaintShortScale);
        //long scale
        canvas.drawLine(mCursorStartX, mLongScaleY, mCursorStartX + mLength + 1, mLongScaleY, mPaintLongScale);
        //cursor
        canvas.drawLine(mCursorRealX, mCursorScaleStartY,
                mCursorRealX, mCursorScaleStopY, mPaintCursor);

        for (int i = 0; i < mTexts.length; i++) {
            canvas.drawText(mTexts[i], mCursorStartX + mLongAadvance*i, mCursorScaleStartY - 20f, mTextPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mHadMoved = false;
                mTrackingPointerId = event.getPointerId(0);
                mDownX = event.getX();
                Log.d(TAG, "[ACTION_DOWN] mDownX = " + mDownX + " mTrackingPointerId = " + mTrackingPointerId);
                mCurrentOffset = mCursorRealX;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //取正在点击的手指的id
                int actionIndex = event.getActionIndex();
                mTrackingPointerId = event.getPointerId(actionIndex);
                Log.d(TAG, "[ACTION_POINTER_DOWN] actionIndex = " + actionIndex + " mTrackingPointerId = " + mTrackingPointerId);
                mDownX = event.getX(actionIndex);
                mCurrentOffset = mCursorRealX;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //获取抬起手指的index
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                Log.d(TAG, "[ACTION_POINTER_UP]111 pointerId = " + pointerId
                        + "actionIndex = " + actionIndex + " mTrackingPointerId = " + mTrackingPointerId);
                //判断是否最后按下的那根手指抬起了,是->把事件交给倒数第二根手指; 否->事件依然是给最后的手指处理
                if(pointerId == mTrackingPointerId) {
                    int newIndex = 0;
                    if (actionIndex == event.getPointerCount() - 1) {
                        //倒数第二根手指
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        //倒数第一根手指
                        newIndex = event.getPointerCount() - 1;
                    }
                    mTrackingPointerId = event.getPointerId(newIndex);
                    mDownX = event.getX(newIndex);
                    mCurrentOffset = mCursorRealX;
                }
                Log.d(TAG, "[ACTION_POINTER_UP]222  mTrackingPointerId = " + mTrackingPointerId);
                break;
            case MotionEvent.ACTION_MOVE:
                actionIndex = event.findPointerIndex(mTrackingPointerId);
                Log.d(TAG, "[ACTION_MOVE] pointerIndex = " + actionIndex + " mTrackingPointerId = " + mTrackingPointerId);
                if (actionIndex < 0) break;
                float dx = event.getX(actionIndex) - mDownX;
                if (Math.abs(dx) < 1.0f) {
                    mHadMoved = false;
                    break;
                } else {
                    mHadMoved = true;
                }
                mCursorRealX = dx * 1.5f + mCurrentOffset;
                if (mCursorRealX < mCursorLimitStartX) {
                    mCursorRealX = mCursorLimitStartX;
                }
                if (mCursorRealX > mCursorLimitEndX) {
                    mCursorRealX = mCursorLimitEndX;
                }
                //float indexf = (mCursorRealX - mCursorStartX - mCursorStartOffsetX) / mAadvance;
                int index = Math.round((mCursorRealX - mCursorLimitStartX) / mAadvance);
                //Log.d(TAG, " ff = " + ff + "  indexf = " + Float.toString(indexf));
                if (mNewIndex != index) {
                    mNewIndex = index;
                    if (mOnIndexChangeListener != null) {
                        mOnIndexChangeListener.onIndexChange(mNewIndex);
                    }
                }

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                actionIndex = event.getActionIndex();
                Log.d(TAG, "[ACTION_UP] mHadMoved = " + mHadMoved + " actionIndex = " + actionIndex);
                float translationX = 0f;
                float f = (mCursorRealX - mCursorLimitStartX)/(mAadvance/2);
                float multiple = (float) Math.floor(f);
                float f2 = f - multiple;
                if (!mHadMoved) {
                    if (mDownX < mCursorLimitStartX) {
                        mDownX = mCursorLimitStartX;
                    }
                    if (mDownX > mCursorLimitEndX) {
                        mDownX = mCursorLimitEndX;
                    }

                    int indexUp = Math.round((mDownX - mCursorLimitStartX) / mAadvance);
                    if (mNewIndex != indexUp) {
                        mNewIndex = indexUp;
                        if (mOnIndexChangeListener != null) {
                            mOnIndexChangeListener.onIndexChange(mNewIndex);
                        }
                    }
                    f = (float) Math.round((mDownX - mCursorRealX) / mAadvance);
                    cursorAutocorrection(mAadvance * f);
                    break;
                }
                //Log.d(TAG, " ACTION_UP  f = " + Float.toString(f));
                //Log.d(TAG, " ACTION_UP  f2 = " + Float.toString(f2));
                //Log.d(TAG, " ACTION_UP  multiple = " + Float.toString(multiple));
                if (multiple % 2==0) {
                    translationX = -(mAadvance/2 * f2);
                } else {
                    translationX = mAadvance/2 * (1 - f2);
                }
                cursorAutocorrection(translationX);
                break;
        }
        return true;
    }

    private void cursorAutocorrection(float translationX) {
        Log.d(TAG, "[cursorAutocorrection] translationX = " + translationX + " mIsPlayingAnimation = " + mIsPlayingAnimation);
        if (mIsPlayingAnimation) return;
        ObjectAnimator translationCursorX = new ObjectAnimator().ofFloat(this, "cursorX", 0f, translationX);
        translationCursorX.setDuration(100);
        translationCursorX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsPlayingAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldValue = 0;
                mIsPlayingAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                oldValue = 0;
                mIsPlayingAnimation = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                oldValue = 0;
            }
        });
        translationCursorX.start();
    }

    boolean mIsPlayingAnimation;
    float oldValue = 0f;
    private void setCursorX(float value) {
        float offsetValue = value - oldValue;
        oldValue = value;
        //Log.d(TAG, "[setCursorX] value = " + value);
        mCursorRealX += offsetValue;
        invalidate();
    }

    public void setValue(String value) {
        for (int index = 0; index < mTexts.length; index ++) {
            if (mTexts[index].equals(value)) {
                Log.d(TAG, "HPF[setValue] index = " + index);
                if (mNewIndex != index) {
                    mNewIndex = index;
                    if (mOnIndexChangeListener != null) {
                        mOnIndexChangeListener.onIndexChange(mNewIndex);
                    }
                }
                invalidate();
            }
        }
    }
}
