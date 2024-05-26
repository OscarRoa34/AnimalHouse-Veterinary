package co.edu.uptc.models;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import co.edu.uptc.Interfaces.VetInterface;

public class VetManager implements VetInterface.Model {
    private List<Pet> pets;
    private List<Person> persons;
    private List<Vaccine> vaccines;
    private List<Appointment> appointments;
    @SuppressWarnings("unused")
    private VetInterface.Presenter presenter;

    public VetManager() {
        pets = new ArrayList<>();
        persons = new ArrayList<>();
        vaccines = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    @Override
    public void setPresenter(VetInterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void registerPet(Pet pet) {
        if (pet != null && !pets.equals(pet)) {
            pets.add(pet);
        }
    }

    @Override
    public void editPet(int petId, Pet newPet) {
        for (int i = 0; i < pets.size(); i++) {
            Pet pet = pets.get(i);
            if (pet.getPetId() == petId) {
                pets.set(i, newPet);
                break;
            }
        }
    }

    @Override
    public void removePetById(int id) {
        for (Pet pet : pets) {
            if (pet.getPetId() == id) {
                pets.remove(pet);
                break;
            }
        }
    }

    @Override
    public void registerPerson(Person person) {
        if (person != null && !persons.contains(person)) {
            persons.add(person);
        }
    }

    @Override
    public void editPerson(int personId, Person newPerson) {
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            if (person.getPersonId() == personId) {
                persons.set(i, newPerson);
                break;
            }
        }
    }

    @Override
    public void removePersonById(int id) {
        for (Person person : persons) {
            if (person.getPersonId() == id) {
                persons.remove(person);
                break;
            }
        }
    }

    @Override
    public void registerVaccine(Vaccine vaccine) {
        if (vaccine != null && !vaccines.contains(vaccine)) {
            vaccines.add(vaccine);
        }
    }

    @Override
    public void editVaccine(int vaccineId, Vaccine newVaccine) {
        for (int i = 0; i < vaccines.size(); i++) {
            Vaccine vaccine = vaccines.get(i);
            if (vaccine.getVaccineId() == vaccineId) {
                vaccines.set(i, newVaccine);
                break;
            }
        }
    }

    @Override
    public void removeVaccineById(int id) {
        for (Vaccine vaccine : vaccines) {
            if (vaccine.getVaccineId() == id) {
                vaccines.remove(vaccine);
                break;
            }
        }
    }

    @Override
    public void registerAppointment(Appointment appointment) {
        if (appointment != null && !appointments.contains(appointment)) {
            appointments.add(appointment);
        }
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> appointmentsByDate = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentDate().isEqual(date)) {
                appointmentsByDate.add(appointment);
            }
        }
        return appointmentsByDate;
    }

    @Override
    public List<Appointment> getAppointmentsByPerson(Person person) {
        List<Appointment> appointmentsByPerson = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getResponsible().equals(person)) {
                appointmentsByPerson.add(appointment);
            }
        }
        return appointmentsByPerson;
    }

    @Override
    public List<Pet> getPetsWithVaccinesExpiring(LocalDate date) {
        List<Pet> petsWithVaccinesExpiring = new ArrayList<>();

        for (Appointment appointment : appointments) {
            for (LocalDate expireDate : appointment.calculateVaccineExpireDates()) {
                if (expireDate.isEqual(date)) {
                    petsWithVaccinesExpiring.add(appointment.getPet());
                    break;
                }
            }
        }
        return petsWithVaccinesExpiring;
    }

    public void saveAppointmentsToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), appointments);
    }

    public void loadAppointmentsFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        appointments = objectMapper.readValue(new File(filePath),
                new TypeReference<List<Appointment>>() {
                });
    }

    public void saveVaccinesToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), vaccines);
    }

    public void loadVaccinesFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        vaccines = objectMapper.readValue(new File(filePath),
                new TypeReference<List<Vaccine>>() {
                });
    }

    public void savePersonsToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), persons);
    }

    public void loadPersonsFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        persons = objectMapper.readValue(new File(filePath),
                new TypeReference<List<Person>>() {
                });
    }

    public void savePetsToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), pets);
    }

    public void loadPetsFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        pets = objectMapper.readValue(new File(filePath),
                new TypeReference<List<Pet>>() {
                });
    }

    @Override
    public List<Pet> getPets() {
        return pets;
    }

    @Override
    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public List<Vaccine> getVaccines() {
        return vaccines;
    }

    @Override
    public List<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public Person getPersonById(int id) {
        for (Person person : persons) {
            if (person.getPersonId() == id) {
                return person;
            }
        }
        return null;
    }

    @Override
    public Pet getPetById(int id) {
        for (Pet pet : pets) {
            if (pet.getPetId() == id) {
                return pet;
            }
        }
        return null;
    }
}
