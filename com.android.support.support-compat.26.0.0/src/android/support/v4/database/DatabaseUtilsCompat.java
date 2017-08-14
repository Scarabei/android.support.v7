package android.support.v4.database;

import android.text.TextUtils;

public final class DatabaseUtilsCompat {
   public static String concatenateWhere(String a, String b) {
      if (TextUtils.isEmpty(a)) {
         return b;
      } else {
         return TextUtils.isEmpty(b) ? a : "(" + a + ") AND (" + b + ")";
      }
   }

   public static String[] appendSelectionArgs(String[] originalValues, String[] newValues) {
      if (originalValues != null && originalValues.length != 0) {
         String[] result = new String[originalValues.length + newValues.length];
         System.arraycopy(originalValues, 0, result, 0, originalValues.length);
         System.arraycopy(newValues, 0, result, originalValues.length, newValues.length);
         return result;
      } else {
         return newValues;
      }
   }
}
