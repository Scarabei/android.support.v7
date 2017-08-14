package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Notification.Action;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(19)
class NotificationCompatKitKat {
   public static NotificationCompatBase.Action getAction(Notification notif, int actionIndex, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory) {
      Action action = notif.actions[actionIndex];
      Bundle actionExtras = null;
      SparseArray actionExtrasMap = notif.extras.getSparseParcelableArray("android.support.actionExtras");
      if (actionExtrasMap != null) {
         actionExtras = (Bundle)actionExtrasMap.get(actionIndex);
      }

      return NotificationCompatJellybean.readAction(factory, remoteInputFactory, action.icon, action.title, action.actionIntent, actionExtras);
   }

   public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {
      private android.app.Notification.Builder b;
      private Bundle mExtras;
      private List mActionExtrasList = new ArrayList();
      private RemoteViews mContentView;
      private RemoteViews mBigContentView;

      public Builder(Context context, Notification n, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, RemoteViews tickerView, int number, PendingIntent contentIntent, PendingIntent fullScreenIntent, Bitmap largeIcon, int progressMax, int progress, boolean progressIndeterminate, boolean showWhen, boolean useChronometer, int priority, CharSequence subText, boolean localOnly, ArrayList people, Bundle extras, String groupKey, boolean groupSummary, String sortKey, RemoteViews contentView, RemoteViews bigContentView) {
         this.b = (new android.app.Notification.Builder(context)).setWhen(n.when).setShowWhen(showWhen).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(useChronometer).setPriority(priority).setProgress(progressMax, progress, progressIndeterminate);
         this.mExtras = new Bundle();
         if (extras != null) {
            this.mExtras.putAll(extras);
         }

         if (people != null && !people.isEmpty()) {
            this.mExtras.putStringArray("android.people", (String[])people.toArray(new String[people.size()]));
         }

         if (localOnly) {
            this.mExtras.putBoolean("android.support.localOnly", true);
         }

         if (groupKey != null) {
            this.mExtras.putString("android.support.groupKey", groupKey);
            if (groupSummary) {
               this.mExtras.putBoolean("android.support.isGroupSummary", true);
            } else {
               this.mExtras.putBoolean("android.support.useSideChannel", true);
            }
         }

         if (sortKey != null) {
            this.mExtras.putString("android.support.sortKey", sortKey);
         }

         this.mContentView = contentView;
         this.mBigContentView = bigContentView;
      }

      public void addAction(NotificationCompatBase.Action action) {
         this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.b, action));
      }

      public android.app.Notification.Builder getBuilder() {
         return this.b;
      }

      public Notification build() {
         SparseArray actionExtrasMap = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
         if (actionExtrasMap != null) {
            this.mExtras.putSparseParcelableArray("android.support.actionExtras", actionExtrasMap);
         }

         this.b.setExtras(this.mExtras);
         Notification notification = this.b.build();
         if (this.mContentView != null) {
            notification.contentView = this.mContentView;
         }

         if (this.mBigContentView != null) {
            notification.bigContentView = this.mBigContentView;
         }

         return notification;
      }
   }
}
