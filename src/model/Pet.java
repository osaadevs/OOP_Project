package model;


public class Pet {
   private int id;
   private String name;
   private String type;
   private String breed;
   private int age;
   private int shelterId;
   private String status;


   public Pet(int id, String name, String type, String breed, int age, int shelterId, String status) {
       this.id = id;
       this.name = name;
       this.type = type;
       this.breed = breed;
       this.age = age;
       this.shelterId = shelterId;
       this.status = status;
   }


   public int getId() { return id; }
   public String getName() { return name; }
   public String getType() { return type; }
   public String getBreed() { return breed; }
   public int getAge() { return age; }
   public int getShelterId() { return shelterId; }
   public String getStatus() { return status; }


   @Override
   public String toString() {
       return name + " (" + breed + " " + type + ")";
   }
}
