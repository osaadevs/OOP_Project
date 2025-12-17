package model;


public class AdoptionApplication {
   private int appId;
   private int userId;
   private String userName; // Helper for display
   private int petId;
   private String petName;  // Helper for display
   private String status;


   public AdoptionApplication(int appId, int userId, String userName, int petId, String petName, String status) {
       this.appId = appId;
       this.userId = userId;
       this.userName = userName;
       this.petId = petId;
       this.petName = petName;
       this.status = status;
   }


   public int getAppId() { return appId; }
   public int getUserId() { return userId; }
   public String getUserName() { return userName; }
   public int getPetId() { return petId; }
   public String getPetName() { return petName; }
   public String getStatus() { return status; }


   @Override
   public String toString() {
       return "App #" + appId + " (" + status + ")";
   }
}
