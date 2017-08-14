package android.support.v7.app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.DialogFragment;

public class AppCompatDialogFragment extends DialogFragment {
   public Dialog onCreateDialog(Bundle savedInstanceState) {
      return new AppCompatDialog(this.getContext(), this.getTheme());
   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   public void setupDialog(Dialog dialog, int style) {
      if (dialog instanceof AppCompatDialog) {
         AppCompatDialog acd = (AppCompatDialog)dialog;
         switch(style) {
         case 3:
            dialog.getWindow().addFlags(24);
         case 1:
         case 2:
            acd.supportRequestWindowFeature(1);
         }
      } else {
         super.setupDialog(dialog, style);
      }

   }
}
