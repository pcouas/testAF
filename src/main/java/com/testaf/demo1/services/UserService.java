package com.testaf.demo1.services;

import com.testaf.demo1.model.User;
import com.testaf.demo1.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final int majorite = 18;
    public static final String france = "FR";
    public static final String youngErrorMessage = "User is too younger";
    public static final String notFrenchErrormessage ="User is not French";
    public static final String userNotFoundErrorMessage ="User not found";
    public static final String badInputParameterErrorMessage ="Bad input parameter";
    public static final String badInputDateParameterErrorMessage ="Bad input BirthDate parameter";
    public static final String badInputCountryParameterErrorMessage ="Bad input Country parameter";
    public static final String badInputPhoneParameterErrorMessage ="Bad input Phone parameter";
    final String phoneFrenchRegex = "^\n"
            + "    (?:(?:\\+|00)33|0)     # Dialing code\n"
            + "    \\s*[1-9]              # First number (from 1 to 9)\n"
            + "    (?:[\\s.-]*\\d{2}){4}   # End of the phone number\n"
            + "$";
    Pattern p = Pattern.compile(phoneFrenchRegex, Pattern.CASE_INSENSITIVE | Pattern.COMMENTS);

    @Autowired
    private UserRepository userRepo;
    /**
     * @param user
     * @return
     * @throws IllegalArgumentException
     *
     * Retrouver un seul utilisateur
     */
    public User findByUser(final User user) throws  IllegalArgumentException,NoSuchElementException {
        log.debug("FinD BEGIN");

        if (user == null) {
            log.error("Exception user not Found");
            throw new IllegalArgumentException(badInputParameterErrorMessage);
        }

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id");
        Example<User> example = Example.of(user, exampleMatcher);

        long count = userRepo.count(example);
        if(count==1) {
            Optional<User> exampleResult = userRepo.findOne(example);
            if (exampleResult.isPresent()) {
                log.debug("Find END ok");
                return exampleResult.get();
            }
        }
        if(count>1) {
            List<User> exampleResult = userRepo.findAll(example);
            return exampleResult.get(0);
        }
        log.error("FinD END ko");
        throw new NoSuchElementException(userNotFoundErrorMessage);
    }

    /**
     *
     * @param user
     * @return
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    public List<User> findAllByUser(final User user) throws  IllegalArgumentException,NoSuchElementException {
        log.debug("BEGIN");

        if (user == null) {
            log.error("Exception user not Found");
              throw new IllegalArgumentException(badInputParameterErrorMessage);
        }

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id");
        Example<User> example = Example.of(user, exampleMatcher);
        List<User> exampleResult = userRepo.findAll(example);

        if (exampleResult.size()>0) {
            log.debug("END ok");
            return exampleResult;
        }
        log.error("END ko");
        throw new NoSuchElementException(userNotFoundErrorMessage);
    }


    /**
     *
     * @param user
     * @throws IllegalArgumentException
     *
     * Tester les parametres d'entree
     */
    private void testInputCreateParam(User user) throws  IllegalArgumentException{

        if (user != null) {

            //----- test date
            if(user.getBirthdate()==null){
                throw new IllegalArgumentException(badInputDateParameterErrorMessage);
            }

            long age = LocalDate.from(user.getBirthdate()).until(LocalDate.now(), ChronoUnit.YEARS);
            log.info("age {}", age);
            if (age < majorite) {
                log.info("probleme validation parametres age:{}", age);
                throw new IllegalArgumentException(youngErrorMessage);
            }
            log.info("birthdate {}", user.getBirthdate());

            //----- test Phone si pas vide TODO voir si je le laisse
            if(user.getPhoneNumber()!=null && user.getPhoneNumber().trim().length()>0){
                Matcher matcher = p.matcher(user.getPhoneNumber());
                if (!matcher.find()){
                    log.info("probleme validation parametres phone");
                    throw new IllegalArgumentException(badInputPhoneParameterErrorMessage);
                }
            }

            //---- test pays
            if (user.getCountry()==null) {
                log.info("probleme validation parametres country");
                throw new IllegalArgumentException(badInputCountryParameterErrorMessage);
            }

            if (france.equals(user.getCountry().getCode())) {
                log.debug("PARAM OK");
                return;
             } else {
                log.info("probleme validation parametres country:{}", user.getCountry().getCode());
                throw new IllegalArgumentException(notFrenchErrormessage);
            }
        }
        log.error("Parametres entree KO");
        throw new IllegalArgumentException(badInputParameterErrorMessage);
    }

    /**
     *
     * @param user
     * @return
     * @throws IllegalArgumentException
     *
     * Creation d'un utilisateur
     */
    public User create(User user)  throws  IllegalArgumentException{
        log.debug("BEGIN");
        testInputCreateParam(user);
         return userRepo.save(user);
    }


}
