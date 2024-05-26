package co.edu.uptc.VetPresenter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.Interfaces.VetInterface;
import co.edu.uptc.Interfaces.VetInterface.*;
import co.edu.uptc.models.Appointment;
import co.edu.uptc.models.Person;
import co.edu.uptc.models.Pet;
import co.edu.uptc.models.Vaccine;

public class VetPresenter implements VetInterface.Presenter {
    @SuppressWarnings("unused")
    private View view;
    private Model model;

    @Override
    public void setView(VetInterface.View view) {
        this.view = view;
    }

    @Override
    public void setModel(VetInterface.Model model) {
        this.model = model;
    }

    @Override
    public void registerPet(Pet pet) {
        model.registerPet(pet);
    }

    @Override
    public Pet createPet(int petId, String petName, String specie, String breed, int petAge, Person owner) {
        return new Pet(petId, petName, specie, breed, petAge, owner, null);
    }

    @Override
    public void editPet(int petId, Pet newPet) {
        model.editPet(petId, newPet);
    }

    @Override
    public void removePetById(int id) {
        model.removePetById(id);
    }

    @Override
    public void registerPerson(Person person) {
        model.registerPerson(person);
    }

    @Override
    public Person createPerson(int personId, String personName, String personLastName, int personAge, String docType,
            String docNumber) {
        return new Person(personId, personName, personLastName, personAge, docType, docNumber);
    }

    @Override
    public void editPerson(int personId, Person newPerson) {
        model.editPerson(personId, newPerson);
    }

    @Override
    public void removePersonById(int id) {
        model.removePersonById(id);
    }

    @Override
    public void registerVaccine(Vaccine vaccine) {
        model.registerVaccine(vaccine);
    }

    @Override
    public Vaccine createVaccine(int vaccineId, String vaccineName, int vaccineLifeSpan) {
        return new Vaccine(vaccineId, vaccineName, vaccineLifeSpan);
    }

    @Override
    public void editVaccine(int vaccineId, Vaccine newVaccine) {
        model.editVaccine(vaccineId, newVaccine);
    }

    @Override
    public void removeVaccineById(int id) {
        model.removeVaccineById(id);
    }

    @Override
    public void registerAppointment(Appointment appointment) {
        model.registerAppointment(appointment);
    }

    @Override
    public Appointment createAppointment(int contadorId, List<Integer> petIds, List<Integer> vaccineIds,
            List<Integer> userIds) {
        List<Pet> selectedPets = new ArrayList<>();
        for (int petId : petIds) {
            Pet pet = getPetById(petId);
            selectedPets.add(pet);
        }

        List<Vaccine> selectedVaccines = new ArrayList<>();
        for (int vaccineId : vaccineIds) {
            Vaccine vaccine = getVaccineById(vaccineId);
            selectedVaccines.add(vaccine);
        }

        List<Person> selectedUsers = new ArrayList<>();
        for (int userId : userIds) {
            Person user = getPersonById(userId);
            selectedUsers.add(user);
        }

        return new Appointment(contadorId, LocalDate.now(), selectedPets.get(0),
                selectedUsers.get(0), selectedVaccines);
    }

    private Vaccine getVaccineById(int vaccineId) {
        List<Vaccine> vaccines = model.getVaccines();

        for (Vaccine vaccine : vaccines) {
            if (vaccine.getVaccineId() == vaccineId) {
                return vaccine;
            }
        }
        return null;
    }

    private Pet getPetById(int petId) {
        List<Pet> pets = model.getPets();
        for (Pet pet : pets) {
            if (pet.getPetId() == petId) {
                return pet;
            }
        }
        return null;
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        model.removeAppointment(appointment);
    }

    // @Override
    public void getAppointmentsByDate(LocalDate date) {
        // List<Appointment> appointments = model.getAppointmentsByDate(date);
        // view.showAppointmentsByDate(appointments);
    }

    // @Override
    public void getAppointmentsByPerson(Person person) {
        // List<Appointment> appointments = model.getAppointmentsByPerson(person);
        // view.showAppointmentsByPerson(appointments);
    }

    // @Override
    public void getPetsWithVaccinesExpiring(LocalDate date) {
        // List<Pet> pets = model.getPetsWithVaccinesExpiring(date);
        // view.showPetsWithVaccinesExpiring(pets);
    }

    @Override
    public List<Pet> getPets() {
        return model.getPets();
    }

    @Override
    public List<Person> getPersons() {
        return model.getPersons();
    }

    @Override
    public List<Vaccine> getVaccines() {
        return model.getVaccines();
    }

    public List<Appointment> getAppointments() {
        return model.getAppointments();
    }

    @Override
    public Person getPersonById(int id) {
        return model.getPersonById(id);
    }

}
