package co.edu.uptc.models;

import java.io.File;
import java.io.IOException;
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

    @SuppressWarnings("unlikely-arg-type")
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
    public void saveAppointmentsToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), appointments);
    }

    @Override
    public void loadAppointmentsFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        if (file.exists()) {
            appointments = objectMapper.readValue(file, new TypeReference<List<Appointment>>() {
            });
        } else {
            appointments = new ArrayList<>();
            file.createNewFile();
            saveAppointmentsToJson(filePath);
        }
    }

    @Override
    public void saveVaccinesToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), vaccines);
    }

    @Override
    public void loadVaccinesFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        if (file.exists()) {
            vaccines = objectMapper.readValue(file, new TypeReference<List<Vaccine>>() {
            });
        } else {
            vaccines = new ArrayList<>();
            file.createNewFile();
            saveVaccinesToJson(filePath);
        }
    }

    @Override
    public void savePersonsToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), persons);
    }

    @Override
    public void loadPersonsFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        if (file.exists()) {
            persons = objectMapper.readValue(file, new TypeReference<List<Person>>() {
            });
        } else {
            persons = new ArrayList<>();
            file.createNewFile();
            savePersonsToJson(filePath);
        }
    }

    @Override
    public void savePetsToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), pets);
    }

    @Override
    public void loadPetsFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        if (file.exists()) {
            pets = objectMapper.readValue(file, new TypeReference<List<Pet>>() {
            });
        } else {
            pets = new ArrayList<>();
            file.createNewFile();
            savePetsToJson(filePath);
        }
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

    @Override
    public int getUserLastId() {
        if (persons == null || persons.isEmpty()) {
            return -1;
        }
        return persons.get(persons.size() - 1).getPersonId() + 1;
    }

    @Override
    public int getPetLastId() {
        if (pets == null || pets.isEmpty()) {
            return -1;
        }
        return pets.get(pets.size() - 1).getPetId() + 1;
    }

    @Override
    public int getVaccineLastId() {
        if (vaccines == null || vaccines.isEmpty()) {
            return -1;
        }
        return vaccines.get(vaccines.size() - 1).getVaccineId() + 1;
    }

    @Override
    public int getAppointmentLastId() {
        if (appointments == null || appointments.isEmpty()) {
            return 0;
        }
        return appointments.get(appointments.size() - 1).getAppointmentId() + 1;
    }

}
