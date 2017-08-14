package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public class ContentFrameLayout extends FrameLayout {
   private TypedValue mMinWidthMajor;
   private TypedValue mMinWidthMinor;
   private TypedValue mFixedWidthMajor;
   private TypedValue mFixedWidthMinor;
   private TypedValue mFixedHeightMajor;
   private TypedValue mFixedHeightMinor;
   private final Rect mDecorPadding;
   private ContentFrameLayout.OnAttachListener mAttachListener;

   public ContentFrameLayout(Context context) {
      this(context, (AttributeSet)null);
   }

   public ContentFrameLayout(Context context, AttributeSet attrs) {
      this(context, attrs, 0);
   }

   public ContentFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      this.mDecorPadding = new Rect();
   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   public void dispatchFitSystemWindows(Rect insets) {
      this.fitSystemWindows(insets);
   }

   public void setAttachListener(ContentFrameLayout.OnAttachListener attachListener) {
      this.mAttachListener = attachListener;
   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   public void setDecorPadding(int left, int top, int right, int bottom) {
      this.mDecorPadding.set(left, top, right, bottom);
      if (ViewCompat.isLaidOut(this)) {
         this.requestLayout();
      }

   }

   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      DisplayMetrics metrics = this.getContext().getResources().getDisplayMetrics();
      boolean isPortrait = metrics.widthPixels < metrics.heightPixels;
      int widthMode = MeasureSpec.getMode(widthMeasureSpec);
      int heightMode = MeasureSpec.getMode(heightMeasureSpec);
      boolean fixedWidth = false;
      TypedValue tvh;
      int h;
      int heightSize;
      if (widthMode == Integer.MIN_VALUE) {
         tvh = isPortrait ? this.mFixedWidthMinor : this.mFixedWidthMajor;
         if (tvh != null && tvh.type != 0) {
            h = 0;
            if (tvh.type == 5) {
               h = (int)tvh.getDimension(metrics);
            } else if (tvh.type == 6) {
               h = (int)tvh.getFraction((float)metrics.widthPixels, (float)metrics.widthPixels);
            }

            if (h > 0) {
               h -= this.mDecorPadding.left + this.mDecorPadding.right;
               heightSize = MeasureSpec.getSize(widthMeasureSpec);
               widthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(h, heightSize), 1073741824);
               fixedWidth = true;
            }
         }
      }

      if (heightMode == Integer.MIN_VALUE) {
         tvh = isPortrait ? this.mFixedHeightMajor : this.mFixedHeightMinor;
         if (tvh != null && tvh.type != 0) {
            h = 0;
            if (tvh.type == 5) {
               h = (int)tvh.getDimension(metrics);
            } else if (tvh.type == 6) {
               h = (int)tvh.getFraction((float)metrics.heightPixels, (float)metrics.heightPixels);
            }

            if (h > 0) {
               h -= this.mDecorPadding.top + this.mDecorPadding.bottom;
               heightSize = MeasureSpec.getSize(heightMeasureSpec);
               heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(h, heightSize), 1073741824);
            }
         }
      }

      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      int width = this.getMeasuredWidth();
      boolean measure = false;
      widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, 1073741824);
      if (!fixedWidth && widthMode == Integer.MIN_VALUE) {
         TypedValue tv = isPortrait ? this.mMinWidthMinor : this.mMinWidthMajor;
         if (tv != null && tv.type != 0) {
            int min = 0;
            if (tv.type == 5) {
               min = (int)tv.getDimension(metrics);
            } else if (tv.type == 6) {
               min = (int)tv.getFraction((float)metrics.widthPixels, (float)metrics.widthPixels);
            }

            if (min > 0) {
               min -= this.mDecorPadding.left + this.mDecorPadding.right;
            }

            if (width < min) {
               widthMeasureSpec = MeasureSpec.makeMeasureSpec(min, 1073741824);
               measure = true;
            }
         }
      }

      if (measure) {
         super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      }

   }

   public TypedValue getMinWidthMajor() {
      if (this.mMinWidthMajor == null) {
         this.mMinWidthMajor = new TypedValue();
      }

      return this.mMinWidthMajor;
   }

   public TypedValue getMinWidthMinor() {
      if (this.mMinWidthMinor == null) {
         this.mMinWidthMinor = new TypedValue();
      }

      return this.mMinWidthMinor;
   }

   public TypedValue getFixedWidthMajor() {
      if (this.mFixedWidthMajor == null) {
         this.mFixedWidthMajor = new TypedValue();
      }

      return this.mFixedWidthMajor;
   }

   public TypedValue getFixedWidthMinor() {
      if (this.mFixedWidthMinor == null) {
         this.mFixedWidthMinor = new TypedValue();
      }

      return this.mFixedWidthMinor;
   }

   public TypedValue getFixedHeightMajor() {
      if (this.mFixedHeightMajor == null) {
         this.mFixedHeightMajor = new TypedValue();
      }

      return this.mFixedHeightMajor;
   }

   public TypedValue getFixedHeightMinor() {
      if (this.mFixedHeightMinor == null) {
         this.mFixedHeightMinor = new TypedValue();
      }

      return this.mFixedHeightMinor;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      if (this.mAttachListener != null) {
         this.mAttachListener.onAttachedFromWindow();
      }

   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      if (this.mAttachListener != null) {
         this.mAttachListener.onDetachedFromWindow();
      }

   }

   public interface OnAttachListener {
      void onDetachedFromWindow();

      void onAttachedFromWindow();
   }
}