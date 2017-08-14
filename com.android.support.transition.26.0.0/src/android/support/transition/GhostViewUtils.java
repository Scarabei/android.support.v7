package android.support.transition;

import android.graphics.Matrix;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;

class GhostViewUtils {
   private static final GhostViewImpl.Creator CREATOR;

   static GhostViewImpl addGhost(View view, ViewGroup viewGroup, Matrix matrix) {
      return CREATOR.addGhost(view, viewGroup, matrix);
   }

   static void removeGhost(View view) {
      CREATOR.removeGhost(view);
   }

   static {
      if (VERSION.SDK_INT >= 21) {
         CREATOR = new GhostViewApi21.Creator();
      } else {
         CREATOR = new GhostViewApi14.Creator();
      }

   }
}
