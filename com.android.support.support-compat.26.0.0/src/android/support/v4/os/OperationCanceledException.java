package android.support.v4.os;

public class OperationCanceledException extends RuntimeException {
   public OperationCanceledException() {
      this((String)null);
   }

   public OperationCanceledException(String message) {
      super(message != null ? message : "The operation has been canceled.");
   }
}
