package com.google.android.gms.games.multiplayer;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;

public interface Invitations {
   Intent getInvitationInboxIntent(GoogleApiClient var1);

   void registerInvitationListener(GoogleApiClient var1, OnInvitationReceivedListener var2);

   void unregisterInvitationListener(GoogleApiClient var1);

   /** @deprecated */
   @Deprecated
   PendingResult loadInvitations(GoogleApiClient var1);

   /** @deprecated */
   @Deprecated
   PendingResult loadInvitations(GoogleApiClient var1, int var2);

   public interface LoadInvitationsResult extends Releasable, Result {
      InvitationBuffer getInvitations();
   }
}
