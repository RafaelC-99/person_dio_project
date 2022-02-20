package one.digitalinnovation.personapi.service;

import java.util.stream.Collectors;
import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.execptions.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    private PersonRepository personRepository;

    /*@Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }*/

    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPeson = personRepository.save(personToSave);
        return MessageResponseDTO.builder().message("Created person with ID " + savedPeson.getId()).build();
    }

    public List<PersonDTO> listAll() {
       List<Person> allPeople = personRepository.findAll();
        return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException{
        Person person = verifyIfExists(id);
        /*Optional<Person> optionalPerson = personRepository.findById(id);
        if(optionalPerson.isEmpty()){
            throw new PersonNotFoudException(id);
        }*/
        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        //personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO update(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);

        MessageResponseDTO messageResponse = createMessageResponse("Person successfully updated with ID ", updatedPerson.getId());

        return messageResponse;
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException{
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

    }

    private MessageResponseDTO createMessageResponse(String s, Long id) {
        return MessageResponseDTO.builder()
                .message(s + id)
                .build();
    }
}
