package com.testaf.demo1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.testaf.demo1.dto.Param;
import com.testaf.demo1.model.Country;
import com.testaf.demo1.model.Gender;
import com.testaf.demo1.model.User;
import com.testaf.demo1.repo.CountryRepository;
import com.testaf.demo1.repo.UserRepository;
import com.testaf.demo1.services.CountryService;
import com.testaf.demo1.services.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureRestDocs
public class UserControllerTestGenDoc {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    @MockBean
    private UserService userService;

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CountryRepository countryRepo;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private Country countryFR;


    @Before
    public void setUp() {
        //    countryFR = countryRepo.getByCode("FR");
        countryFR = new Country();
        countryFR.setId(1L);
        countryFR.setCode("FR");


    }

    //TODO pose soucis
    @Test
    public void testCreateOneUser() throws Exception {
/*
        User user = new User();
        user.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        user.setUserName("Hugo");
        user.setGender(Gender.M);
        user.setCountry(countryFR);
        user.setPhoneNumber("0601020304");

        when(this.userRepository.save(any(User.class))).thenReturn(user);
        Param p=new Param();
        p.setUser(user);

        //   when(this.userRepository.save(any(User.class))).thenReturn(p);
//        when(userController.createUser(p)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));
        when(userService.create(user)).thenReturn(user);



        FieldDescriptor[] userDescriptor = getUserFieldDescriptor();

        //todo C'est le responseEntity et pas l'objet user en lui meme

       // this.mockMvc.perform(post("/api/user/create").content(this.objectMapper.writeValueAsString(u))
       //         .contentType(MediaType.APPLICATION_JSON))
       //         .andExpect(status().isCreated())
       //         .andExpect(jsonPath("$.userName", is(user.getName()))
       //         .andExpect(jsonPath("$.gender").value(Gender.M.name()))
       //         .andExpect(jsonPath("$.country").value(countryFR))
       //         .andExpect(jsonPath("$.phoneNumber").value("0601020304"))
       //         .andExpect(jsonPath("$.birthdate").value("16/01/2001"))
       //         .andDo(document("shouldCreateUser",
       //                 requestFields(userDescriptor),
       //                 responseFields(userDescriptor)));


        this.mockMvc.perform(post("/api/user/create").content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName", is(user.getUserName()))
                );
*/

    }


    @Test
    public void testGetOneUser() throws Exception {
/*
        User mockUser = new User();
        mockUser.setId(1L);
        when(this.userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

//TODO
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/user/findOne/{xxxId}", "34"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(34))
                .andDo(document("shouldGetOneUser",
                        pathParameters(
                                parameterWithName("id").description("The id of the user to retrieve")
                        ),
                        responseFields(this.getUserFieldDescriptor())));

     */
    }


    private FieldDescriptor[] getUserFieldDescriptor() {
        return new FieldDescriptor[]{fieldWithPath("birthdate").description("The birth date of the user").type(Integer.class.getSimpleName()),
                fieldWithPath("userName").description("The name of the user").type(String.class.getSimpleName()),
                fieldWithPath("gender").description("The gender of the user (F or M)").type(Gender.class.getSimpleName()),
                fieldWithPath("phoneNumber").description("The cell phone number of the user").type(String.class.getSimpleName()),
                fieldWithPath("id").description("The unique id of the user").optional().type(Long.class.getSimpleName())}; //todo manque le country
    }


}
