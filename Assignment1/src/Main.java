import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 1;
        CowinData cowindata = new CowinData();
        while(choice != 8){
            System.out.println("\nCoWin Portal");
            System.out.println("1. Add Vaccine");
            System.out.println("2. Register Hospital");
            System.out.println("3. Register Citizen");
            System.out.println("4. Add Slot for Vaccination");
            System.out.println("5. Book Slot for Vaccination");
            System.out.println("6. List all slots for a hospital");
            System.out.println("7. Check Vaccination Status");
            System.out.println("8. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            System.out.println();

            switch(choice) {
                case 1:
                    System.out.println("Enter Vaccine Name: ");
                    String name = sc.next();
                    System.out.println("Enter Number Of Doses: ");
                    int noOfDoses = sc.nextInt();
                    int gapBetweenDoses = 0;
                    if(noOfDoses == 1) {
                        gapBetweenDoses = 0;
                    }
                    else {
                        System.out.println("Enter the gap between the Doses: ");
                        gapBetweenDoses = sc.nextInt();
                    }
                    cowindata.addVaccine(new Vaccine(name, noOfDoses, gapBetweenDoses));
                    // Displays details about the added vaccine.
                    // using array list.
                    cowindata.getListOfVaccines().get(cowindata.getListOfVaccines().size() - 1).displayVaccineData();
                    break;
                case 2:
                    System.out.println("Enter Hospital Name: ");
                    String nameHospital = sc.next();
                    System.out.println("Enter Hospital Pincode: ");
                    int pincode = sc.nextInt();
                    cowindata.addHospital(new Hospital(nameHospital, pincode));
                    cowindata.getListOfHospitals().get(cowindata.getListOfHospitals().size() - 1).displayHospitalData();
                    break;
                case 3:
                    System.out.println("Enter Citizen Name: ");
                    String nameCitizen = sc.next();
                    System.out.println("Enter Citizen Age: ");
                    int ageCitizen = sc.nextInt();
                    System.out.println("Enter Citizen uniqueID: ");
                    long uniqueID = sc.nextLong();
                    if(ageCitizen <= 18 || cowindata.isCitizenUnique(cowindata.getListOfCitizens(), uniqueID) == 0){
                        if(ageCitizen<=18){
                            System.out.println("Age of Citizen must be above 18.");
                        }
                        break;
                    }
                    cowindata.addCitizen(new Citizen(nameCitizen, ageCitizen, uniqueID));
                    cowindata.getListOfCitizens().get(cowindata.getListOfCitizens().size() - 1).displayCitizenData();
                    break;
                case 4:
                    System.out.println("Enter Hospital ID:");
                    int hUniqueID = sc.nextInt();
                    System.out.println("Enter number of Slots to be added: ");
                    Hospital h = cowindata.findHospital(cowindata.getListOfHospitals(), hUniqueID);
                    int numberslot = sc.nextInt();
                    h.setNumberOfSlots(numberslot);
                    while(numberslot > 0) {
                        System.out.println("Enter Day Number:");
                        int daySlot = sc.nextInt();
                        System.out.println("Enter Quantity:");
                        int quan = sc.nextInt();
                        int cnt = 0;
                        System.out.println("Select Vaccine: ");
                        for(Vaccine vc: cowindata.getListOfVaccines()){
                            System.out.println(cnt + ". " + vc.getName());
                            cnt++;
                        }
                        Vaccine vac = cowindata.getListOfVaccines().get(sc.nextInt());
                        // Adding the Slot
                        cowindata.addSlot(new Slot(h, vac, daySlot, quan));
                        // Displaying the added Slot.
                        cowindata.getListOfSlots().get(cowindata.getListOfSlots().size() - 1).displaySlotData();
                        // adding the slot to the list of a class
                        h.getListOfSlots().add(cowindata.getListOfSlots().get(cowindata.getListOfSlots().size() - 1));
                        numberslot--;
                    }
                    break;
                case 5:
                    System.out.println("Enter patient Unique ID:");
                    long idunique = sc.nextLong();
                    Citizen c = cowindata.findCitizen(cowindata.getListOfCitizens(), idunique);
                    if(c.getStatus() == Status.FULLY){
                        System.out.println("Already Fully Vaccinated");
                        break;
                    }
                    System.out.println("1. Search by area\n" +
                                       "2. Search by Vaccine\n" +
                                       "3. Exit");
                    System.out.println("Enter option: ");
                    int menuChoice = sc.nextInt();
                    if(menuChoice == 1){
                        System.out.println("Enter PinCode: ");
                        cowindata.searchByPincode(cowindata.getListOfHospitals(), sc.nextInt());
                        System.out.println("Enter hospital id: ");
                        Hospital hos = cowindata.findHospital(cowindata.getListOfHospitals(), sc.nextInt());
                        int flg = hos.displayAllSlots(c.getDayOfNextDose());
                        if(flg == 1) {
                            System.out.println("Choose Slot: ");
                            c.setSlot(hos.getListOfSlots().get(sc.nextInt()));
                            c.setStatus();
                            c.displayVaccinationStatus();
                        }
                    }
                    if(menuChoice == 2){
                        System.out.println("Enter Vaccine name: ");
                        String vaccineName = sc.next();
                        cowindata.searchByVaccineName(vaccineName, cowindata.getListOfHospitals(), cowindata.getListOfVaccines());
                        System.out.println("Enter hospital id: ");
                        Hospital hos = cowindata.findHospital(cowindata.getListOfHospitals(), sc.nextInt());
                        int flg = hos.displayAllSlotsByVaccineName(c.getDayOfNextDose(), vaccineName);
                        if(flg == 1) {
                            System.out.println("Choose Slot: ");
                            Slot s = hos.getListOfSlots().get(sc.nextInt());
                            c.setSlot(s);
                            c.setStatus();
                            c.displayVaccinationStatus();
                        }
                    }
                    break;
                case 6:
                    System.out.println("Enter Hospital Id: ");
                    cowindata.findHospital(cowindata.getListOfHospitals(), sc.nextInt()).displayAllSlotss();
                    break;
                case 7:
                    System.out.println("Enter Patient ID: ");
                    long iddunique = sc.nextLong();
                    cowindata.findCitizen(cowindata.getListOfCitizens(), iddunique).displayStatus();
                    break;
                case 8:
                    System.out.println("Exiting the Program");
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }
}
