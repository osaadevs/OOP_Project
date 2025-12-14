package model;


public class RehomeRequest {
   private int reqId;
   private int userId;
   private String username;
   private String petName;
   private String petType;
   private String petBreed;
   private int age;
   private String reason;
   private String status;


   public RehomeRequest(int reqId, int userId, String username, String petName, String petType, String petBreed, int age, String reason, String status) {
       this.reqId = reqId;
       this.userId = userId;
       this.username = username;
       this.petName = petName;
       this.petType = petType;
       this.petBreed = petBreed;
       this.age = age;
       this.reason = reason;
       this.status = status;
   }


   public int getReqId() { return reqId; }
   public int getUserId() { return userId; }
   public String getUsername() { return username; }
   public String getPetName() { return petName; }
   public String getPetType() { return petType; }
   public String getPetBreed() { return petBreed; }
   public int getAge() { return age; }
   public String getReason() { return reason; }
   public String getStatus() { return status; }
}
