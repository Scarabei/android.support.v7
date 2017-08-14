package android.support.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

public class TransitionSet extends Transition {
   private ArrayList mTransitions = new ArrayList();
   private boolean mPlayTogether = true;
   private int mCurrentListeners;
   private boolean mStarted = false;
   public static final int ORDERING_TOGETHER = 0;
   public static final int ORDERING_SEQUENTIAL = 1;

   public TransitionSet() {
   }

   public TransitionSet(Context context, AttributeSet attrs) {
      super(context, attrs);
      TypedArray a = context.obtainStyledAttributes(attrs, Styleable.TRANSITION_SET);
      int ordering = TypedArrayUtils.getNamedInt(a, (XmlResourceParser)attrs, "transitionOrdering", 0, 0);
      this.setOrdering(ordering);
      a.recycle();
   }

   @NonNull
   public TransitionSet setOrdering(int ordering) {
      switch(ordering) {
      case 0:
         this.mPlayTogether = true;
         break;
      case 1:
         this.mPlayTogether = false;
         break;
      default:
         throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + ordering);
      }

      return this;
   }

   public int getOrdering() {
      return this.mPlayTogether ? 0 : 1;
   }

   @NonNull
   public TransitionSet addTransition(@NonNull Transition transition) {
      this.mTransitions.add(transition);
      transition.mParent = this;
      if (this.mDuration >= 0L) {
         transition.setDuration(this.mDuration);
      }

      return this;
   }

   public int getTransitionCount() {
      return this.mTransitions.size();
   }

   public Transition getTransitionAt(int index) {
      return index >= 0 && index < this.mTransitions.size() ? (Transition)this.mTransitions.get(index) : null;
   }

   @NonNull
   public TransitionSet setDuration(long duration) {
      super.setDuration(duration);
      if (this.mDuration >= 0L) {
         int numTransitions = this.mTransitions.size();

         for(int i = 0; i < numTransitions; ++i) {
            ((Transition)this.mTransitions.get(i)).setDuration(duration);
         }
      }

      return this;
   }

   @NonNull
   public TransitionSet setStartDelay(long startDelay) {
      return (TransitionSet)super.setStartDelay(startDelay);
   }

   @NonNull
   public TransitionSet setInterpolator(@Nullable TimeInterpolator interpolator) {
      return (TransitionSet)super.setInterpolator(interpolator);
   }

   @NonNull
   public TransitionSet addTarget(@NonNull View target) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).addTarget(target);
      }

      return (TransitionSet)super.addTarget(target);
   }

   @NonNull
   public TransitionSet addTarget(@IdRes int targetId) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).addTarget(targetId);
      }

      return (TransitionSet)super.addTarget(targetId);
   }

   @NonNull
   public TransitionSet addTarget(@NonNull String targetName) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).addTarget(targetName);
      }

      return (TransitionSet)super.addTarget(targetName);
   }

   @NonNull
   public TransitionSet addTarget(@NonNull Class targetType) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).addTarget(targetType);
      }

      return (TransitionSet)super.addTarget(targetType);
   }

   @NonNull
   public TransitionSet addListener(@NonNull Transition.TransitionListener listener) {
      return (TransitionSet)super.addListener(listener);
   }

   @NonNull
   public TransitionSet removeTarget(@IdRes int targetId) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).removeTarget(targetId);
      }

      return (TransitionSet)super.removeTarget(targetId);
   }

   @NonNull
   public TransitionSet removeTarget(@NonNull View target) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).removeTarget(target);
      }

      return (TransitionSet)super.removeTarget(target);
   }

   @NonNull
   public TransitionSet removeTarget(@NonNull Class target) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).removeTarget(target);
      }

      return (TransitionSet)super.removeTarget(target);
   }

   @NonNull
   public TransitionSet removeTarget(@NonNull String target) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).removeTarget(target);
      }

      return (TransitionSet)super.removeTarget(target);
   }

   @NonNull
   public Transition excludeTarget(@NonNull View target, boolean exclude) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).excludeTarget(target, exclude);
      }

      return super.excludeTarget(target, exclude);
   }

   @NonNull
   public Transition excludeTarget(@NonNull String targetName, boolean exclude) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).excludeTarget(targetName, exclude);
      }

      return super.excludeTarget(targetName, exclude);
   }

   @NonNull
   public Transition excludeTarget(int targetId, boolean exclude) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).excludeTarget(targetId, exclude);
      }

      return super.excludeTarget(targetId, exclude);
   }

   @NonNull
   public Transition excludeTarget(@NonNull Class type, boolean exclude) {
      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).excludeTarget(type, exclude);
      }

      return super.excludeTarget(type, exclude);
   }

   @NonNull
   public TransitionSet removeListener(@NonNull Transition.TransitionListener listener) {
      return (TransitionSet)super.removeListener(listener);
   }

   public void setPathMotion(PathMotion pathMotion) {
      super.setPathMotion(pathMotion);

      for(int i = 0; i < this.mTransitions.size(); ++i) {
         ((Transition)this.mTransitions.get(i)).setPathMotion(pathMotion);
      }

   }

   @NonNull
   public TransitionSet removeTransition(@NonNull Transition transition) {
      this.mTransitions.remove(transition);
      transition.mParent = null;
      return this;
   }

   private void setupStartEndListeners() {
      TransitionSet.TransitionSetListener listener = new TransitionSet.TransitionSetListener(this);
      Iterator var2 = this.mTransitions.iterator();

      while(var2.hasNext()) {
         Transition childTransition = (Transition)var2.next();
         childTransition.addListener(listener);
      }

      this.mCurrentListeners = this.mTransitions.size();
   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   protected void createAnimators(ViewGroup sceneRoot, TransitionValuesMaps startValues, TransitionValuesMaps endValues, ArrayList startValuesList, ArrayList endValuesList) {
      long startDelay = this.getStartDelay();
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         Transition childTransition = (Transition)this.mTransitions.get(i);
         if (startDelay > 0L && (this.mPlayTogether || i == 0)) {
            long childStartDelay = childTransition.getStartDelay();
            if (childStartDelay > 0L) {
               childTransition.setStartDelay(startDelay + childStartDelay);
            } else {
               childTransition.setStartDelay(startDelay);
            }
         }

         childTransition.createAnimators(sceneRoot, startValues, endValues, startValuesList, endValuesList);
      }

   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   protected void runAnimators() {
      if (this.mTransitions.isEmpty()) {
         this.start();
         this.end();
      } else {
         this.setupStartEndListeners();
         Transition previousTransition;
         if (!this.mPlayTogether) {
            for(int i = 1; i < this.mTransitions.size(); ++i) {
               previousTransition = (Transition)this.mTransitions.get(i - 1);
               final Transition nextTransition = (Transition)this.mTransitions.get(i);
               previousTransition.addListener(new TransitionListenerAdapter() {
                  public void onTransitionEnd(@NonNull Transition transition) {
                     nextTransition.runAnimators();
                     transition.removeListener(this);
                  }
               });
            }

            Transition firstTransition = (Transition)this.mTransitions.get(0);
            if (firstTransition != null) {
               firstTransition.runAnimators();
            }
         } else {
            Iterator var5 = this.mTransitions.iterator();

            while(var5.hasNext()) {
               previousTransition = (Transition)var5.next();
               previousTransition.runAnimators();
            }
         }

      }
   }

   public void captureStartValues(@NonNull TransitionValues transitionValues) {
      if (this.isValidTarget(transitionValues.view)) {
         Iterator var2 = this.mTransitions.iterator();

         while(var2.hasNext()) {
            Transition childTransition = (Transition)var2.next();
            if (childTransition.isValidTarget(transitionValues.view)) {
               childTransition.captureStartValues(transitionValues);
               transitionValues.mTargetedTransitions.add(childTransition);
            }
         }
      }

   }

   public void captureEndValues(@NonNull TransitionValues transitionValues) {
      if (this.isValidTarget(transitionValues.view)) {
         Iterator var2 = this.mTransitions.iterator();

         while(var2.hasNext()) {
            Transition childTransition = (Transition)var2.next();
            if (childTransition.isValidTarget(transitionValues.view)) {
               childTransition.captureEndValues(transitionValues);
               transitionValues.mTargetedTransitions.add(childTransition);
            }
         }
      }

   }

   void capturePropagationValues(TransitionValues transitionValues) {
      super.capturePropagationValues(transitionValues);
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         ((Transition)this.mTransitions.get(i)).capturePropagationValues(transitionValues);
      }

   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   public void pause(View sceneRoot) {
      super.pause(sceneRoot);
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         ((Transition)this.mTransitions.get(i)).pause(sceneRoot);
      }

   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   public void resume(View sceneRoot) {
      super.resume(sceneRoot);
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         ((Transition)this.mTransitions.get(i)).resume(sceneRoot);
      }

   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   protected void cancel() {
      super.cancel();
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         ((Transition)this.mTransitions.get(i)).cancel();
      }

   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   void forceToEnd(ViewGroup sceneRoot) {
      super.forceToEnd(sceneRoot);
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         ((Transition)this.mTransitions.get(i)).forceToEnd(sceneRoot);
      }

   }

   TransitionSet setSceneRoot(ViewGroup sceneRoot) {
      super.setSceneRoot(sceneRoot);
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         ((Transition)this.mTransitions.get(i)).setSceneRoot(sceneRoot);
      }

      return this;
   }

   void setCanRemoveViews(boolean canRemoveViews) {
      super.setCanRemoveViews(canRemoveViews);
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         ((Transition)this.mTransitions.get(i)).setCanRemoveViews(canRemoveViews);
      }

   }

   public void setEpicenterCallback(Transition.EpicenterCallback epicenterCallback) {
      super.setEpicenterCallback(epicenterCallback);
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         ((Transition)this.mTransitions.get(i)).setEpicenterCallback(epicenterCallback);
      }

   }

   String toString(String indent) {
      String result = super.toString(indent);

      for(int i = 0; i < this.mTransitions.size(); ++i) {
         result = result + "\n" + ((Transition)this.mTransitions.get(i)).toString(indent + "  ");
      }

      return result;
   }

   public Transition clone() {
      TransitionSet clone = (TransitionSet)super.clone();
      clone.mTransitions = new ArrayList();
      int numTransitions = this.mTransitions.size();

      for(int i = 0; i < numTransitions; ++i) {
         clone.addTransition(((Transition)this.mTransitions.get(i)).clone());
      }

      return clone;
   }

   static class TransitionSetListener extends TransitionListenerAdapter {
      TransitionSet mTransitionSet;

      TransitionSetListener(TransitionSet transitionSet) {
         this.mTransitionSet = transitionSet;
      }

      public void onTransitionStart(@NonNull Transition transition) {
         if (!this.mTransitionSet.mStarted) {
            this.mTransitionSet.start();
            this.mTransitionSet.mStarted = true;
         }

      }

      public void onTransitionEnd(@NonNull Transition transition) {
         --this.mTransitionSet.mCurrentListeners;
         if (this.mTransitionSet.mCurrentListeners == 0) {
            this.mTransitionSet.mStarted = false;
            this.mTransitionSet.end();
         }

         transition.removeListener(this);
      }
   }
}
