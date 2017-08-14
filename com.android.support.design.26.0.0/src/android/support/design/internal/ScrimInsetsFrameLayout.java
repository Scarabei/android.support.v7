package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.R.style;
import android.support.design.R.styleable;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ScrimInsetsFrameLayout extends FrameLayout {
   Drawable mInsetForeground;
   Rect mInsets;
   private Rect mTempRect;

   public ScrimInsetsFrameLayout(Context context) {
      this(context, (AttributeSet)null);
   }

   public ScrimInsetsFrameLayout(Context context, AttributeSet attrs) {
      this(context, attrs, 0);
   }

   public ScrimInsetsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      this.mTempRect = new Rect();
      TypedArray a = context.obtainStyledAttributes(attrs, styleable.ScrimInsetsFrameLayout, defStyleAttr, style.Widget_Design_ScrimInsetsFrameLayout);
      this.mInsetForeground = a.getDrawable(styleable.ScrimInsetsFrameLayout_insetForeground);
      a.recycle();
      this.setWillNotDraw(true);
      ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
         public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
            if (null == ScrimInsetsFrameLayout.this.mInsets) {
               ScrimInsetsFrameLayout.this.mInsets = new Rect();
            }

            ScrimInsetsFrameLayout.this.mInsets.set(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            ScrimInsetsFrameLayout.this.onInsetsChanged(insets);
            ScrimInsetsFrameLayout.this.setWillNotDraw(!insets.hasSystemWindowInsets() || ScrimInsetsFrameLayout.this.mInsetForeground == null);
            ViewCompat.postInvalidateOnAnimation(ScrimInsetsFrameLayout.this);
            return insets.consumeSystemWindowInsets();
         }
      });
   }

   public void draw(@NonNull Canvas canvas) {
      super.draw(canvas);
      int width = this.getWidth();
      int height = this.getHeight();
      if (this.mInsets != null && this.mInsetForeground != null) {
         int sc = canvas.save();
         canvas.translate((float)this.getScrollX(), (float)this.getScrollY());
         this.mTempRect.set(0, 0, width, this.mInsets.top);
         this.mInsetForeground.setBounds(this.mTempRect);
         this.mInsetForeground.draw(canvas);
         this.mTempRect.set(0, height - this.mInsets.bottom, width, height);
         this.mInsetForeground.setBounds(this.mTempRect);
         this.mInsetForeground.draw(canvas);
         this.mTempRect.set(0, this.mInsets.top, this.mInsets.left, height - this.mInsets.bottom);
         this.mInsetForeground.setBounds(this.mTempRect);
         this.mInsetForeground.draw(canvas);
         this.mTempRect.set(width - this.mInsets.right, this.mInsets.top, width, height - this.mInsets.bottom);
         this.mInsetForeground.setBounds(this.mTempRect);
         this.mInsetForeground.draw(canvas);
         canvas.restoreToCount(sc);
      }

   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      if (this.mInsetForeground != null) {
         this.mInsetForeground.setCallback(this);
      }

   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      if (this.mInsetForeground != null) {
         this.mInsetForeground.setCallback((Callback)null);
      }

   }

   protected void onInsetsChanged(WindowInsetsCompat insets) {
   }
}