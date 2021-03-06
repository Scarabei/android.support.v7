package android.support.v4.content;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;

public final class IntentCompat {
   private static final IntentCompat.IntentCompatBaseImpl IMPL;
   /** @deprecated */
   @Deprecated
   public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
   /** @deprecated */
   @Deprecated
   public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
   /** @deprecated */
   @Deprecated
   public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
   /** @deprecated */
   @Deprecated
   public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
   public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
   public static final String EXTRA_START_PLAYBACK = "android.intent.extra.START_PLAYBACK";
   public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
   /** @deprecated */
   @Deprecated
   public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
   /** @deprecated */
   @Deprecated
   public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;

   /** @deprecated */
   @Deprecated
   public static Intent makeMainActivity(ComponentName mainActivity) {
      return Intent.makeMainActivity(mainActivity);
   }

   public static Intent makeMainSelectorActivity(String selectorAction, String selectorCategory) {
      return IMPL.makeMainSelectorActivity(selectorAction, selectorCategory);
   }

   /** @deprecated */
   @Deprecated
   public static Intent makeRestartActivityTask(ComponentName mainActivity) {
      return Intent.makeRestartActivityTask(mainActivity);
   }

   static {
      if (VERSION.SDK_INT >= 15) {
         IMPL = new IntentCompat.IntentCompatApi15Impl();
      } else {
         IMPL = new IntentCompat.IntentCompatBaseImpl();
      }

   }

   @RequiresApi(15)
   static class IntentCompatApi15Impl extends IntentCompat.IntentCompatBaseImpl {
      public Intent makeMainSelectorActivity(String selectorAction, String selectorCategory) {
         return Intent.makeMainSelectorActivity(selectorAction, selectorCategory);
      }
   }

   static class IntentCompatBaseImpl {
      public Intent makeMainSelectorActivity(String selectorAction, String selectorCategory) {
         Intent intent = new Intent(selectorAction);
         intent.addCategory(selectorCategory);
         return intent;
      }
   }
}
