import java.util.ArrayList;
class Vaccine{
    private final String name;
    private final int noOfDoses;
    private final int gapBetweenDoses;
    public Vaccine(String name, int noOfDoses, int gapBetweenDoses){
        this.name = name;
        this.noOfDoses = noOfDoses;
        this.gapBetweenDoses = gapBetweenDoses;
    }
    //  getters
    public String getName(){
        return this.name;
    }
    public int getNoOfDoses(){
        return this.noOfDoses;
    }
    public int getGapInDoses(){
        return this.gapBetweenDoses;
    }
    public void displayVaccineData(){
        System.out.println("Name of the Vaccine: " + this.getName());
        System.out.println("Number of Doses: " + this.getNoOfDoses());
        System.out.println("Gap Between Doses: "+ this.getGapInDoses());
        System.out.println();
    }
}
class Hospital{
    private final String name;
    private final int pincode;
    private final int uniqueID;
    private int numberOfSlots;
    private ArrayList<Slot> listOfSlots = new ArrayList<Slot>();
    public Hospital(String name, int pincode){
        this.name = name;
        this.pincode = pincode;
        this.uniqueID = (int) (Math.floor(Math.random() * 100_000L) + 900_000L);
    }

    // Getters

    public String getName() {
        return this.name;
    }
    public int getUniqueID() {
        return this.uniqueID;
    }

    public int getPincode() {
        return this.pincode;
    }
    public int getNumberOfSlots(){

        return this.numberOfSlots;
    }
    public ArrayList<Slot> getListOfSlots(){

        return this.listOfSlots;
    }
    public void displayHospitalData(){
        System.out.println("Hospital Name: " + this.getName());
        System.out.println("Pincode: " + this.getPincode());
        System.out.println("UniqueID: " + this.getUniqueID());
    }
    public void setNumberOfSlots(int numberOfSlots){
        this.numberOfSlots = numberOfSlots;
    }

    public void addListOfSlots(Slot s){
        this.listOfSlots.add(s);
    }
    // Displays Slots (by pincode) which the citizen is eligible for.
    public int displayAllSlots(int day) {
        int i = 0;
        int flg = 0;
        for(Slot s: listOfSlots){
            if(s.getDay() >= day && s.getQuantity() > 0) {
                System.out.println(i + "-> Day: " + s.getDay() + " Vaccine: " + s.getVaccineName() + " Available Qty: " + s.getQuantity());
                flg = 1;
            }
            i++;
        }
        if(flg == 0){
            System.out.println("No slots available");
        }
        return flg;
    }
    // Displays all the slots by Vaccine Name.
    public int displayAllSlotsByVaccineName(int day, String vaccineName) {
        int i = 0;
        int flg = 0;
        for(Slot s: listOfSlots){
            if((s.getDay() >= day) && (s.getQuantity() > 0) && (s.getVaccineName().equals(vaccineName))) {
                System.out.println(i + "-> Day: " + s.getDay() + " Vaccine: " + s.getVaccineName() + " Available Qty: " + s.getQuantity());
                flg = 1;
            }
            i++;
        }
        if(flg == 0){
            System.out.println("No slots available");
        }
        return flg;
    }

    // displays all slots of every day
    public void displayAllSlotss() {
        int i = 0;
        for(Slot s: listOfSlots){
            System.out.println(i + "-> Day: " + s.getDay() + " Vaccine: " + s.getVaccineName() + " Available Qty: " + s.getQuantity());
            i++;
        }
    }
}
class Citizen{
    private final String name;
    private int age;
    private final long uniqueID;
    private Status status;
    private Slot slot;
    private int numberOfDosesGiven = 0;
    private int dayOfNextDose = 1;

    public Citizen(String name, int age, long uniqueID){
        this.name = name;
        this.age = age;
        this.uniqueID = uniqueID;
        this.status = Status.NOT;
    }
    // Getters
    public int getDayOfNextDose() {
        return this.dayOfNextDose;
    }
    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
    public long getUniqueID(){
        return this.uniqueID;
    }
    public int getNumberOfDosesGiven(){
        return this.numberOfDosesGiven;
    }
    public Slot getSlot(){
        return this.slot;
    }
    public Status getStatus(){
        return this.status;
    }
    //Setters

    public void setNumberOfDosesGiven(int num){
        this.numberOfDosesGiven = num;
    }
    public void setDayOfNextDose(int n){
        this.dayOfNextDose = n;
    }
    public void setStatus(){
        if(getSlot().getVaccine().getNoOfDoses() > 1){
            if(this.status == Status.NOT){
                this.status = Status.PARTIALLY;
            }
            // check if more than 2 required
            if(this.status == Status.PARTIALLY && getNumberOfDosesGiven() == getSlot().getVaccine().getNoOfDoses()){
                this.status = Status.FULLY;
            }
        }
        if (getSlot().getVaccine().getNoOfDoses() == 1){
            this.status = Status.FULLY;
        }
    }
    public void setSlot(Slot s){
        this.slot = s;
        slot.setQuantity(slot.getQuantity()-1);
        setNumberOfDosesGiven(getNumberOfDosesGiven() + 1);
        setDayOfNextDose(getSlot().getDay() + getSlot().getVaccine().getGapInDoses());
    }

    public void displayCitizenData(){
        System.out.println("Name of the Citizen: " + this.getName());
        System.out.println("Age of the Citizen: " + this.getAge());
        System.out.println("UniqueID of the Citizen "+ this.getUniqueID());
        if(this.getAge() < 18){
            System.out.println("Only above 18 are allowed");
        }
        System.out.println();
    }
    public void displayStatus(){
        if(this.status == Status.FULLY){
            System.out.println("FULLY VACCINATED");
            System.out.println("Vaccine Given: " + getSlot().getVaccineName());
            System.out.println("Number of Doses given:" + getNumberOfDosesGiven());
        }
        else if(this.status == Status.PARTIALLY){
            System.out.println("PARTIALLY VACCINATED");
            System.out.println("Vaccine name: "+ getSlot().getVaccineName());
            System.out.println("Number of Doses given:" + getNumberOfDosesGiven());
            System.out.println("Next Dose due date: " + (getSlot().getDay() + getSlot().getVaccine().getGapInDoses()));
        }
        else if(this.status == Status.NOT){
            System.out.print("CITIZEN REGISTERED, ");
            System.out.println("NOT VACCINATED");
        }
    }

    public void displayVaccinationStatus(){
        System.out.println(this.getName() + " vaccinated with " + this.getSlot().getVaccineName());
    }
}
class Slot{
    private Hospital hospital;
    private int day;
    private int quantity;
    private Vaccine vaccine;

    public Slot(Hospital hospital, Vaccine vaccine, int day, int quantity){
        this.hospital = hospital;
        this.vaccine = vaccine;
        this.day = day;
        this.quantity = quantity;
    }
    public int getDay(){
        return this.day;

    }
    public Vaccine getVaccine(){
        return this.vaccine;
    }
    public String getVaccineName(){
        return vaccine.getName();
    }
    public int getQuantity(){
        return this.quantity;
    }
    public void displaySlotData(){
        System.out.print("Slot added by Hospital " + this.hospital.getUniqueID());
        System.out.print(" for Day: " + this.day);
        System.out.println(", Available Quantity: " + this.quantity + " of Vaccine " + this.vaccine.getName());
    }
    public void setQuantity(int quantity1){
        this.quantity = quantity1;
    }

}
// Managed by the ministry.
class CowinData{
    private ArrayList<Vaccine> listOfVaccines = new ArrayList<Vaccine>();
    private ArrayList<Hospital> listOfHospitals = new ArrayList<Hospital>();
    private  ArrayList<Citizen> listOfCitizens = new ArrayList<Citizen>();
    private ArrayList<Slot> listOfSlots = new ArrayList<Slot>();

    public static void searchByPincode(ArrayList<Hospital> listOfHospitals, int pincode){
        for(Hospital h: listOfHospitals){
            if(h.getPincode() == pincode){
                System.out.println(h.getUniqueID() + " " + h.getName());
            }
        }
    }
    public void searchByVaccineName(String vaccineName, ArrayList<Hospital> listOfHospitals, ArrayList<Vaccine> listOfVaccine){
        int flg = 0;
        for(Vaccine va: listOfVaccine){
            if (va.getName().equals(vaccineName)) {
                flg = 1;
                break;
            }
        }
        if(flg == 0){
            System.out.println("Vaccine Not Found");
            return;
        }
        for(Hospital h: listOfHospitals){
            for(Slot s: h.getListOfSlots()){
                if(s.getVaccineName().equals(vaccineName)){
                    System.out.println(h.getUniqueID() + " " + h.getName());
                }
            }
        }
    }
    public Hospital findHospital(ArrayList<Hospital> listOfHospitals, int uniqueID){
        for(Hospital h : listOfHospitals){
            if(h.getUniqueID() == uniqueID){
                return h;
            }

        }
        System.out.println("Unique ID not found.. ");
        return null;
    }
    public Vaccine findVaccine(ArrayList<Vaccine> listOfVaccines, String name){
        for(Vaccine vaccine : listOfVaccines){
            if(vaccine.getName().equals(name)){
                return vaccine;
            }

        }
        System.out.println("Vaccine not found.. ");
        return null;
    }
    public Citizen findCitizen(ArrayList<Citizen> listOfCitizens, long uniqueID){
        for(Citizen c : listOfCitizens){
            if(c.getUniqueID() == uniqueID){
                return c;
            }
        }
        System.out.println("Unique ID not found.. ");
        return null;
    }
    public static int isCitizenUnique(ArrayList<Citizen> listOfCitizens, long uniqueID){

        for(Citizen c : listOfCitizens){
            if(c.getUniqueID() == uniqueID){
                System.out.println("Citizen Already Registered, same unique ID found.");
                return 0;
            }

        }
        return 1;
    }
    public void addVaccine(Vaccine v){
        getListOfVaccines().add(v);
    }
    public void addHospital(Hospital h){
        getListOfHospitals().add(h);
    }
    public void addCitizen(Citizen c){
        getListOfCitizens().add(c);
    }
    public void addSlot(Slot s){
        getListOfSlots().add(s);
    }
    public ArrayList<Vaccine> getListOfVaccines(){
        return this.listOfVaccines;
    }

    public ArrayList<Hospital> getListOfHospitals(){
        return this.listOfHospitals;
    }
    public ArrayList<Citizen> getListOfCitizens(){
        return this.listOfCitizens;
    }
    public ArrayList<Slot> getListOfSlots(){
        return this.listOfSlots;
    }

}

enum Status {
    FULLY, PARTIALLY, NOT
}