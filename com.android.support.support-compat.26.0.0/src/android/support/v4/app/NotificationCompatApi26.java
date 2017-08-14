package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;

@RequiresApi(26)
class NotificationCompatApi26 {
   public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {
      private android.app.Notification.Builder mB;

      Builder(Context context, Notification n, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, RemoteViews tickerView, int number, PendingIntent contentIntent, PendingIntent fullScreenIntent, Bitmap largeIcon, int progressMax, int progress, boolean progressIndeterminate, boolean showWhen, boolean useChronometer, int priority, CharSequence subText, boolean localOnly, String category, ArrayList people, Bundle extras, int color, int visibility, Notification publicVersion, String groupKey, boolean groupSummary, String sortKey, CharSequence[] remoteInputHistory, RemoteViews contentView, RemoteViews bigContentView, RemoteViews headsUpContentView, String channelId, int badgeIcon, String shortcutId, long timeoutMs, boolean colorized, boolean colorizedSet, int groupAlertBehavior) {
         this.mB = (new android.app.Notification.Builder(context, channelId)).setWhen(n.when).setShowWhen(showWhen).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(useChronometer).setPriority(priority).setProgress(progressMax, progress, progressIndeterminate).setLocalOnly(localOnly).setExtras(extras).setGroup(groupKey).setGroupSummary(groupSummary).setSortKey(sortKey).setCategory(category).setColor(color).setVisibility(visibility).setPublicVersion(publicVersion).setRemoteInputHistory(remoteInputHistory).setChannelId(channelId).setBadgeIconType(badgeIcon).setShortcutId(shortcutId).setTimeoutAfter(timeoutMs).setGroupAlertBehavior(groupAlertBehavior);
         if (colorizedSet) {
            this.mB.setColorized(colorized);
         }

         if (contentView != null) {
            this.mB.setCustomContentView(contentView);
         }

         if (bigContentView != null) {
            this.mB.setCustomBigContentView(bigContentView);
         }

         if (headsUpContentView != null) {
            this.mB.setCustomHeadsUpContentView(headsUpContentView);
         }

         Iterator var40 = people.iterator();

         while(var40.hasNext()) {
            String person = (String)var40.next();
            this.mB.addPerson(person);
         }

      }

      public void addAction(NotificationCompatBase.Action action) {
         NotificationCompatApi24.addAction(this.mB, action);
      }

      public android.app.Notification.Builder getBuilder() {
         return this.mB;
      }

      public Notification build() {
         return this.mB.build();
      }
   }
}
