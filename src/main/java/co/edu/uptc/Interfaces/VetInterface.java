package co.edu.uptc.Interfaces;

import java.io.IOException;
import java.util.List;

import co.edu.uptc.Pojos.Appointment;
import co.edu.uptc.Pojos.Person;
import co.edu.uptc.Pojos.Pet;
import co.edu.uptc.Pojos.Vaccine;

public interface VetInterface {

    public interface Model {
        void setPresenter(Presenter presenter);

        void registerPet(Pet pet);

        void editPet(int petId, Pet newPet);

        void removePetById(int id);

        void registerPerson(Person person);

        void editPerson(int personId, Person newPerson);

        void removePersonById(int id);

        void registerVaccine(Vaccine vaccine);

        void editVaccine(int vaccineId, Vaccine newVaccine);

        void removeVaccineById(int id);

        void registerAppointment(Appointment appointment);

        void removeAppointment(Appointment appointment);

        List<Pet> getPets();

        List<Person> getPersons();

        List<Vaccine> getVaccines();

        List<Appointment> getAppointments();

        Person getPersonById(int id);

        Pet getPetById(int id);

        void saveAppointmentsToJson(String filePath) throws IOException;

        void loadAppointmentsFromJson(String filePath) throws IOException;

        void saveVaccinesToJson(String filePath) throws IOException;

        void loadVaccinesFromJson(String filePath) throws IOException;

        void savePersonsToJson(String filePath) throws IOException;

        void loadPersonsFromJson(String filePath) throws IOException;

        void savePetsToJson(String filePath) throws IOException;

        void loadPetsFromJson(String filePath) throws IOException;

        int getUserLastId();

        int getPetLastId();

        int getVaccineLastId();

        int getAppointmentLastId();

    }

    public interface View {
        public void setPresenter(Presenter presenter);

        public void begin();
    }

    public interface Presenter {
        void setView(View view);

        void setModel(Model model);

        void registerPet(Pet pet);

        Pet createPet(int petId, String petName, String specie, String breed, int age, Person owner, int weight);

        void editPet(int petId, Pet newPet);

        void removePetById(int id);

        void registerPerson(Person person);

        Person createPerson(int personId, String personName, String personLastName, int personAge, String docType,
                String docNumber);

        void editPerson(int personId, Person newPerson);

        void removePersonById(int id);

        void registerVaccine(Vaccine vaccine);

        Vaccine createVaccine(int vaccineId, String vaccineName, int vaccineLifeSpan);

        void editVaccine(int vaccineId, Vaccine newVaccine);

        void removeVaccineById(int id);

        void registerAppointment(Appointment appointment);

        Appointment createAppointment(int contadorId, List<Integer> petIds, List<Integer> vaccineIds,
                List<Integer> userIds);

        void removeAppointment(Appointment appointment);

        List<Pet> getPets();

        List<Person> getPersons();

        List<Vaccine> getVaccines();

        List<Appointment> getAppointments();

        Person getPersonById(int id);

        Pet getPetById(int id);

        void saveAppointmentsToJson(String filePath);

        void loadAppointmentsFromJson(String filePath);

        void saveVaccinesToJson(String filePath);

        void loadVaccinesFromJson(String filePath);

        void savePersonsToJson(String filePath);

        void loadPersonsFromJson(String filePath);

        void savePetsToJson(String filePath);

        void loadPetsFromJson(String filePath);

        int getUserLastId();

        int getPetLastId();

        int getVaccineLastId();

        int getAppointmentLastId();
    }

}
