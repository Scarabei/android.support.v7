package android.support.v4.view;

import android.view.MenuItem;

public final class MenuCompat {
   /** @deprecated */
   @Deprecated
   public static void setShowAsAction(MenuItem item, int actionEnum) {
      item.setShowAsAction(actionEnum);
   }
}
