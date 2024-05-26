package co.edu.uptc.Interfaces;

import java.time.LocalDate;
import java.util.List;

import co.edu.uptc.models.Appointment;
import co.edu.uptc.models.Person;
import co.edu.uptc.models.Pet;
import co.edu.uptc.models.Vaccine;

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

        List<Appointment> getAppointmentsByDate(LocalDate date);

        List<Appointment> getAppointmentsByPerson(Person person);

        List<Pet> getPetsWithVaccinesExpiring(LocalDate date);

        Person getPersonById(int id);
    }

    public interface View {
        public void setPresenter(Presenter presenter);

        public void begin();
    }

    public interface Presenter {
        void setView(View view);

        void setModel(Model model);

        void registerPet(Pet pet);

        Pet createPet(int petId, String petName, String specie, String breed, int age, Person owner);

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

        void getAppointmentsByDate(LocalDate date);

        void getAppointmentsByPerson(Person person);

        void getPetsWithVaccinesExpiring(LocalDate date);

        Person getPersonById(int id);
    }

}
