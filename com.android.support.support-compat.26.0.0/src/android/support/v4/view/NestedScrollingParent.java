package android.support.v4.view;

import android.support.annotation.NonNull;
import android.view.View;

public interface NestedScrollingParent {
   boolean onStartNestedScroll(@NonNull View var1, @NonNull View var2, int var3);

   void onNestedScrollAccepted(@NonNull View var1, @NonNull View var2, int var3);

   void onStopNestedScroll(@NonNull View var1);

   void onNestedScroll(@NonNull View var1, int var2, int var3, int var4, int var5);

   void onNestedPreScroll(@NonNull View var1, int var2, int var3, @NonNull int[] var4);

   boolean onNestedFling(@NonNull View var1, float var2, float var3, boolean var4);

   boolean onNestedPreFling(@NonNull View var1, float var2, float var3);

   int getNestedScrollAxes();
}
