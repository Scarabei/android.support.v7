package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@VisibleForTesting
class FastScroller extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
   private static final int STATE_HIDDEN = 0;
   private static final int STATE_VISIBLE = 1;
   private static final int STATE_DRAGGING = 2;
   private static final int DRAG_NONE = 0;
   private static final int DRAG_X = 1;
   private static final int DRAG_Y = 2;
   private static final int ANIMATION_STATE_OUT = 0;
   private static final int ANIMATION_STATE_FADING_IN = 1;
   private static final int ANIMATION_STATE_IN = 2;
   private static final int ANIMATION_STATE_FADING_OUT = 3;
   private static final int SHOW_DURATION_MS = 500;
   private static final int HIDE_DELAY_AFTER_VISIBLE_MS = 1500;
   private static final int HIDE_DELAY_AFTER_DRAGGING_MS = 1200;
   private static final int HIDE_DURATION_MS = 500;
   private static final int SCROLLBAR_FULL_OPAQUE = 255;
   private static final int[] PRESSED_STATE_SET = new int[]{16842919};
   private static final int[] EMPTY_STATE_SET = new int[0];
   private final int mScrollbarMinimumRange;
   private final int mMargin;
   private final StateListDrawable mVerticalThumbDrawable;
   private final Drawable mVerticalTrackDrawable;
   private final int mVerticalThumbWidth;
   private final int mVerticalTrackWidth;
   private final StateListDrawable mHorizontalThumbDrawable;
   private final Drawable mHorizontalTrackDrawable;
   private final int mHorizontalThumbHeight;
   private final int mHorizontalTrackHeight;
   @VisibleForTesting
   int mVerticalThumbHeight;
   @VisibleForTesting
   int mVerticalThumbCenterY;
   @VisibleForTesting
   float mVerticalDragY;
   @VisibleForTesting
   int mHorizontalThumbWidth;
   @VisibleForTesting
   int mHorizontalThumbCenterX;
   @VisibleForTesting
   float mHorizontalDragX;
   private int mRecyclerViewWidth = 0;
   private int mRecyclerViewHeight = 0;
   private RecyclerView mRecyclerView;
   private boolean mNeedVerticalScrollbar = false;
   private boolean mNeedHorizontalScrollbar = false;
   private int mState = 0;
   private int mDragState = 0;
   private final int[] mVerticalRange = new int[2];
   private final int[] mHorizontalRange = new int[2];
   private final ValueAnimator mShowHideAnimator = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
   private int mAnimationState = 0;
   private final Runnable mHideRunnable = new Runnable() {
      public void run() {
         FastScroller.this.hide(500);
      }
   };
   private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
         FastScroller.this.updateScrollPosition(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
      }
   };

   FastScroller(RecyclerView recyclerView, StateListDrawable verticalThumbDrawable, Drawable verticalTrackDrawable, StateListDrawable horizontalThumbDrawable, Drawable horizontalTrackDrawable, int defaultWidth, int scrollbarMinimumRange, int margin) {
      this.mVerticalThumbDrawable = verticalThumbDrawable;
      this.mVerticalTrackDrawable = verticalTrackDrawable;
      this.mHorizontalThumbDrawable = horizontalThumbDrawable;
      this.mHorizontalTrackDrawable = horizontalTrackDrawable;
      this.mVerticalThumbWidth = Math.max(defaultWidth, verticalThumbDrawable.getIntrinsicWidth());
      this.mVerticalTrackWidth = Math.max(defaultWidth, verticalTrackDrawable.getIntrinsicWidth());
      this.mHorizontalThumbHeight = Math.max(defaultWidth, horizontalThumbDrawable.getIntrinsicWidth());
      this.mHorizontalTrackHeight = Math.max(defaultWidth, horizontalTrackDrawable.getIntrinsicWidth());
      this.mScrollbarMinimumRange = scrollbarMinimumRange;
      this.mMargin = margin;
      this.mVerticalThumbDrawable.setAlpha(255);
      this.mVerticalTrackDrawable.setAlpha(255);
      this.mShowHideAnimator.addListener(new FastScroller.AnimatorListener(null));
      this.mShowHideAnimator.addUpdateListener(new FastScroller.AnimatorUpdater(null));
      this.attachToRecyclerView(recyclerView);
   }

   public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
      if (this.mRecyclerView != recyclerView) {
         if (this.mRecyclerView != null) {
            this.destroyCallbacks();
         }

         this.mRecyclerView = recyclerView;
         if (this.mRecyclerView != null) {
            this.setupCallbacks();
         }

      }
   }

   private void setupCallbacks() {
      this.mRecyclerView.addItemDecoration(this);
      this.mRecyclerView.addOnItemTouchListener(this);
      this.mRecyclerView.addOnScrollListener(this.mOnScrollListener);
   }

   private void destroyCallbacks() {
      this.mRecyclerView.removeItemDecoration(this);
      this.mRecyclerView.removeOnItemTouchListener(this);
      this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
      this.cancelHide();
   }

   private void requestRedraw() {
      this.mRecyclerView.invalidate();
   }

   private void setState(int state) {
      if (state == 2 && this.mState != 2) {
         this.mVerticalThumbDrawable.setState(PRESSED_STATE_SET);
         this.cancelHide();
      }

      if (state == 0) {
         this.requestRedraw();
      } else {
         this.show();
      }

      if (this.mState == 2 && state != 2) {
         this.mVerticalThumbDrawable.setState(EMPTY_STATE_SET);
         this.resetHideDelay(1200);
      } else if (state == 1) {
         this.resetHideDelay(1500);
      }

      this.mState = state;
   }

   private boolean isLayoutRTL() {
      return ViewCompat.getLayoutDirection(this.mRecyclerView) == 1;
   }

   public boolean isDragging() {
      return this.mState == 2;
   }

   @VisibleForTesting
   boolean isVisible() {
      return this.mState == 1;
   }

   @VisibleForTesting
   boolean isHidden() {
      return this.mState == 0;
   }

   public void show() {
      switch(this.mAnimationState) {
      case 3:
         this.mShowHideAnimator.cancel();
      case 0:
         this.mAnimationState = 1;
         this.mShowHideAnimator.setFloatValues(new float[]{((Float)this.mShowHideAnimator.getAnimatedValue()).floatValue(), 1.0F});
         this.mShowHideAnimator.setDuration(500L);
         this.mShowHideAnimator.setStartDelay(0L);
         this.mShowHideAnimator.start();
      default:
      }
   }

   public void hide() {
      this.hide(0);
   }

   @VisibleForTesting
   void hide(int duration) {
      switch(this.mAnimationState) {
      case 1:
         this.mShowHideAnimator.cancel();
      case 2:
         this.mAnimationState = 3;
         this.mShowHideAnimator.setFloatValues(new float[]{((Float)this.mShowHideAnimator.getAnimatedValue()).floatValue(), 0.0F});
         this.mShowHideAnimator.setDuration((long)duration);
         this.mShowHideAnimator.start();
      default:
      }
   }

   private void cancelHide() {
      this.mRecyclerView.removeCallbacks(this.mHideRunnable);
   }

   private void resetHideDelay(int delay) {
      this.cancelHide();
      this.mRecyclerView.postDelayed(this.mHideRunnable, (long)delay);
   }

   public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
      if (this.mRecyclerViewWidth == this.mRecyclerView.getWidth() && this.mRecyclerViewHeight == this.mRecyclerView.getHeight()) {
         if (this.mAnimationState != 0) {
            if (this.mNeedVerticalScrollbar) {
               this.drawVerticalScrollbar(canvas);
            }

            if (this.mNeedHorizontalScrollbar) {
               this.drawHorizontalScrollbar(canvas);
            }
         }

      } else {
         this.mRecyclerViewWidth = this.mRecyclerView.getWidth();
         this.mRecyclerViewHeight = this.mRecyclerView.getHeight();
         this.setState(0);
      }
   }

   private void drawVerticalScrollbar(Canvas canvas) {
      int viewWidth = this.mRecyclerViewWidth;
      int left = viewWidth - this.mVerticalThumbWidth;
      int top = this.mVerticalThumbCenterY - this.mVerticalThumbHeight / 2;
      this.mVerticalThumbDrawable.setBounds(0, 0, this.mVerticalThumbWidth, this.mVerticalThumbHeight);
      this.mVerticalTrackDrawable.setBounds(0, 0, this.mVerticalTrackWidth, this.mRecyclerViewHeight);
      if (this.isLayoutRTL()) {
         this.mVerticalTrackDrawable.draw(canvas);
         canvas.translate((float)this.mVerticalThumbWidth, (float)top);
         canvas.scale(-1.0F, 1.0F);
         this.mVerticalThumbDrawable.draw(canvas);
         canvas.scale(1.0F, 1.0F);
         canvas.translate((float)(-this.mVerticalThumbWidth), (float)(-top));
      } else {
         canvas.translate((float)left, 0.0F);
         this.mVerticalTrackDrawable.draw(canvas);
         canvas.translate(0.0F, (float)top);
         this.mVerticalThumbDrawable.draw(canvas);
         canvas.translate((float)(-left), (float)(-top));
      }

   }

   private void drawHorizontalScrollbar(Canvas canvas) {
      int viewHeight = this.mRecyclerViewHeight;
      int top = viewHeight - this.mHorizontalThumbHeight;
      int left = this.mHorizontalThumbCenterX - this.mHorizontalThumbWidth / 2;
      this.mHorizontalThumbDrawable.setBounds(0, 0, this.mHorizontalThumbWidth, this.mHorizontalThumbHeight);
      this.mHorizontalTrackDrawable.setBounds(0, 0, this.mRecyclerViewWidth, this.mHorizontalTrackHeight);
      canvas.translate(0.0F, (float)top);
      this.mHorizontalTrackDrawable.draw(canvas);
      canvas.translate((float)left, 0.0F);
      this.mHorizontalThumbDrawable.draw(canvas);
      canvas.translate((float)(-left), (float)(-top));
   }

   void updateScrollPosition(int offsetX, int offsetY) {
      int verticalContentLength = this.mRecyclerView.computeVerticalScrollRange();
      int verticalVisibleLength = this.mRecyclerViewHeight;
      this.mNeedVerticalScrollbar = verticalContentLength - verticalVisibleLength > 0 && this.mRecyclerViewHeight >= this.mScrollbarMinimumRange;
      int horizontalContentLength = this.mRecyclerView.computeHorizontalScrollRange();
      int horizontalVisibleLength = this.mRecyclerViewWidth;
      this.mNeedHorizontalScrollbar = horizontalContentLength - horizontalVisibleLength > 0 && this.mRecyclerViewWidth >= this.mScrollbarMinimumRange;
      if (!this.mNeedVerticalScrollbar && !this.mNeedHorizontalScrollbar) {
         if (this.mState != 0) {
            this.setState(0);
         }

      } else {
         float middleScreenPos;
         if (this.mNeedVerticalScrollbar) {
            middleScreenPos = (float)offsetY + (float)verticalVisibleLength / 2.0F;
            this.mVerticalThumbCenterY = (int)((float)verticalVisibleLength * middleScreenPos / (float)verticalContentLength);
            this.mVerticalThumbHeight = Math.min(verticalVisibleLength, verticalVisibleLength * verticalVisibleLength / verticalContentLength);
         }

         if (this.mNeedHorizontalScrollbar) {
            middleScreenPos = (float)offsetX + (float)horizontalVisibleLength / 2.0F;
            this.mHorizontalThumbCenterX = (int)((float)horizontalVisibleLength * middleScreenPos / (float)horizontalContentLength);
            this.mHorizontalThumbWidth = Math.min(horizontalVisibleLength, horizontalVisibleLength * horizontalVisibleLength / horizontalContentLength);
         }

         if (this.mState == 0 || this.mState == 1) {
            this.setState(1);
         }

      }
   }

   public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent ev) {
      boolean handled;
      if (this.mState == 1) {
         boolean insideVerticalThumb = this.isPointInsideVerticalThumb(ev.getX(), ev.getY());
         boolean insideHorizontalThumb = this.isPointInsideHorizontalThumb(ev.getX(), ev.getY());
         if (ev.getAction() != 0 || !insideVerticalThumb && !insideHorizontalThumb) {
            handled = false;
         } else {
            if (insideHorizontalThumb) {
               this.mDragState = 1;
               this.mHorizontalDragX = (float)((int)ev.getX());
            } else if (insideVerticalThumb) {
               this.mDragState = 2;
               this.mVerticalDragY = (float)((int)ev.getY());
            }

            this.setState(2);
            handled = true;
         }
      } else if (this.mState == 2) {
         handled = true;
      } else {
         handled = false;
      }

      return handled;
   }

   public void onTouchEvent(RecyclerView recyclerView, MotionEvent me) {
      if (this.mState != 0) {
         if (me.getAction() == 0) {
            boolean insideVerticalThumb = this.isPointInsideVerticalThumb(me.getX(), me.getY());
            boolean insideHorizontalThumb = this.isPointInsideHorizontalThumb(me.getX(), me.getY());
            if (insideVerticalThumb || insideHorizontalThumb) {
               if (insideHorizontalThumb) {
                  this.mDragState = 1;
                  this.mHorizontalDragX = (float)((int)me.getX());
               } else if (insideVerticalThumb) {
                  this.mDragState = 2;
                  this.mVerticalDragY = (float)((int)me.getY());
               }

               this.setState(2);
            }
         } else if (me.getAction() == 1 && this.mState == 2) {
            this.mVerticalDragY = 0.0F;
            this.mHorizontalDragX = 0.0F;
            this.setState(1);
            this.mDragState = 0;
         } else if (me.getAction() == 2 && this.mState == 2) {
            this.show();
            if (this.mDragState == 1) {
               this.horizontalScrollTo(me.getX());
            }

            if (this.mDragState == 2) {
               this.verticalScrollTo(me.getY());
            }
         }

      }
   }

   public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
   }

   private void verticalScrollTo(float y) {
      int[] scrollbarRange = this.getVerticalRange();
      y = Math.max((float)scrollbarRange[0], Math.min((float)scrollbarRange[1], y));
      if (Math.abs((float)this.mVerticalThumbCenterY - y) >= 2.0F) {
         int scrollingBy = this.scrollTo(this.mVerticalDragY, y, scrollbarRange, this.mRecyclerView.computeVerticalScrollRange(), this.mRecyclerView.computeVerticalScrollOffset(), this.mRecyclerViewHeight);
         if (scrollingBy != 0) {
            this.mRecyclerView.scrollBy(0, scrollingBy);
         }

         this.mVerticalDragY = y;
      }
   }

   private void horizontalScrollTo(float x) {
      int[] scrollbarRange = this.getHorizontalRange();
      x = Math.max((float)scrollbarRange[0], Math.min((float)scrollbarRange[1], x));
      if (Math.abs((float)this.mHorizontalThumbCenterX - x) >= 2.0F) {
         int scrollingBy = this.scrollTo(this.mHorizontalDragX, x, scrollbarRange, this.mRecyclerView.computeHorizontalScrollRange(), this.mRecyclerView.computeHorizontalScrollOffset(), this.mRecyclerViewWidth);
         if (scrollingBy != 0) {
            this.mRecyclerView.scrollBy(scrollingBy, 0);
         }

         this.mHorizontalDragX = x;
      }
   }

   private int scrollTo(float oldDragPos, float newDragPos, int[] scrollbarRange, int scrollRange, int scrollOffset, int viewLength) {
      int scrollbarLength = scrollbarRange[1] - scrollbarRange[0];
      if (scrollbarLength == 0) {
         return 0;
      } else {
         float percentage = (newDragPos - oldDragPos) / (float)scrollbarLength;
         int totalPossibleOffset = scrollRange - viewLength;
         int scrollingBy = (int)(percentage * (float)totalPossibleOffset);
         int absoluteOffset = scrollOffset + scrollingBy;
         return absoluteOffset < totalPossibleOffset && absoluteOffset >= 0 ? scrollingBy : 0;
      }
   }

   @VisibleForTesting
   boolean isPointInsideVerticalThumb(float x, float y) {
      boolean var10000;
      label20: {
         if (this.isLayoutRTL()) {
            if (x > (float)(this.mVerticalThumbWidth / 2)) {
               break label20;
            }
         } else if (x < (float)(this.mRecyclerViewWidth - this.mVerticalThumbWidth)) {
            break label20;
         }

         if (y >= (float)(this.mVerticalThumbCenterY - this.mVerticalThumbHeight / 2) && y <= (float)(this.mVerticalThumbCenterY + this.mVerticalThumbHeight / 2)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   @VisibleForTesting
   boolean isPointInsideHorizontalThumb(float x, float y) {
      return y >= (float)(this.mRecyclerViewHeight - this.mHorizontalThumbHeight) && x >= (float)(this.mHorizontalThumbCenterX - this.mHorizontalThumbWidth / 2) && x <= (float)(this.mHorizontalThumbCenterX + this.mHorizontalThumbWidth / 2);
   }

   @VisibleForTesting
   Drawable getHorizontalTrackDrawable() {
      return this.mHorizontalTrackDrawable;
   }

   @VisibleForTesting
   Drawable getHorizontalThumbDrawable() {
      return this.mHorizontalThumbDrawable;
   }

   @VisibleForTesting
   Drawable getVerticalTrackDrawable() {
      return this.mVerticalTrackDrawable;
   }

   @VisibleForTesting
   Drawable getVerticalThumbDrawable() {
      return this.mVerticalThumbDrawable;
   }

   private int[] getVerticalRange() {
      this.mVerticalRange[0] = this.mMargin;
      this.mVerticalRange[1] = this.mRecyclerViewHeight - this.mMargin;
      return this.mVerticalRange;
   }

   private int[] getHorizontalRange() {
      this.mHorizontalRange[0] = this.mMargin;
      this.mHorizontalRange[1] = this.mRecyclerViewWidth - this.mMargin;
      return this.mHorizontalRange;
   }

   private class AnimatorUpdater implements AnimatorUpdateListener {
      private AnimatorUpdater() {
      }

      public void onAnimationUpdate(ValueAnimator valueAnimator) {
         int alpha = (int)(255.0F * ((Float)valueAnimator.getAnimatedValue()).floatValue());
         FastScroller.this.mVerticalThumbDrawable.setAlpha(alpha);
         FastScroller.this.mVerticalTrackDrawable.setAlpha(alpha);
         FastScroller.this.requestRedraw();
      }

      // $FF: synthetic method
      AnimatorUpdater(Object x1) {
         this();
      }
   }

   private class AnimatorListener extends AnimatorListenerAdapter {
      private boolean mCanceled;

      private AnimatorListener() {
         this.mCanceled = false;
      }

      public void onAnimationEnd(Animator animation) {
         if (this.mCanceled) {
            this.mCanceled = false;
         } else {
            if (((Float)FastScroller.this.mShowHideAnimator.getAnimatedValue()).floatValue() == 0.0F) {
               FastScroller.this.mAnimationState = 0;
               FastScroller.this.setState(0);
            } else {
               FastScroller.this.mAnimationState = 2;
               FastScroller.this.requestRedraw();
            }

         }
      }

      public void onAnimationCancel(Animator animation) {
         this.mCanceled = true;
      }

      // $FF: synthetic method
      AnimatorListener(Object x1) {
         this();
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   private @interface AnimationState {
   }

   @Retention(RetentionPolicy.SOURCE)
   private @interface DragState {
   }

   @Retention(RetentionPolicy.SOURCE)
   private @interface State {
   }
}
