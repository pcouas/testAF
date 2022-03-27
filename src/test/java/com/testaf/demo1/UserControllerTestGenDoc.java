package com.testaf.demo1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.testaf.demo1.controllers.UserController;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test d'integration
 * <p>
 * Permet la generation des fichiers .adoc dans le repertoire generated-sniped
 * la generation des pages html est ensuite effectuée par un mvn package
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
public class UserControllerTestGenDoc {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

    @MockBean
    private UserService userService;

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CountryRepository countryRepo;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private Country countryFR;


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
        countryFR = new Country();
        countryFR.setId(1L);
        countryFR.setCode("FR");
        countryFR.setName("FRANCE");

    }

    /**
     * @throws Exception Creation
     */
    @Test
    @DisplayName("create french user with CountryCode")
    public void createOneUserWithCountryCode() throws Exception {
        String birthDate = "2001-01-16";
        User user = new User();
        user.setBirthdate(LocalDate.parse(birthDate, formatter));
        user.setUserName("Hugo");

        Param p = new Param();
        p.setCountryCode(countryFR.getCode()); //Plus pratique pour l'utilisateur final qui utilise l'API
        p.setUser(user);

        User mockUser = new User();
        mockUser.setBirthdate(LocalDate.parse(birthDate, formatter));
        mockUser.setUserName("Hugo");
        mockUser.setCountry(countryFR);


        when(countryService.getByCode(countryFR.getCode())).thenReturn(countryFR);
        when(userService.create(mockUser)).thenReturn(mockUser);

        this.mockMvc.perform(post("/api/user/create").content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName", is(user.getUserName())))
                .andExpect(jsonPath("$.country").value(countryFR))
                .andExpect(jsonPath("$.birthdate").value(birthDate))
                .andDo(document("{method-name}",
                        requestFields(getUserFieldDescriptorPOSTRequest()),
                        responseFields(getUserFieldDescriptorPOSTResponseFULL())));

    }

    @Test
    @DisplayName("create french user with Country Tableid")
    public void createOneUserWithCountryTableId() throws Exception {
        String birthDate = "2001-01-16";
        User user = new User();
        user.setBirthdate(LocalDate.parse(birthDate, formatter));
        user.setUserName("Hugo");
        user.setGender(Gender.M);
        user.setCountry(countryFR);
        user.setPhoneNumber("0601020304");

        Param p = new Param();
        p.setUser(user);

        when(userService.create(user)).thenReturn(user);

        this.mockMvc.perform(post("/api/user/create").content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName", is(user.getUserName())))
                .andExpect(jsonPath("$.gender").value(Gender.M.name()))
                //      .andExpect(jsonPath("$.country").value(countryFR))
                .andExpect(jsonPath("$.country.code", is(user.getCountry().getCode())))
                .andExpect(jsonPath("$.country.name", is(user.getCountry().getName())))
                .andExpect(jsonPath("$.phoneNumber").value(user.getPhoneNumber()))
                .andExpect(jsonPath("$.birthdate").value(birthDate))
                .andDo(document("{method-name}",
                        requestFields(getUserFieldDescriptorPOSTRequestFULL()),
                        responseFields(getUserFieldDescriptorPOSTResponseFULL())));
    }

    /**
     * @throws Exception
     */
    @Test
    @DisplayName("relecture utilisateur Francais")
    public void findOneUserWithCountryTableId() throws Exception {
        String birthDate = "2001-01-16";
        User mockUser = new User();
        mockUser.setBirthdate(LocalDate.parse(birthDate, formatter));
        mockUser.setUserName("Hugo");
        mockUser.setGender(Gender.M);
        mockUser.setCountry(countryFR);
        mockUser.setPhoneNumber("0601020304");

        when(userService.findByUser(any(User.class))).thenReturn(mockUser);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/user/find")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName", is(mockUser.getUserName())))
                .andExpect(jsonPath("$.gender", is(mockUser.getGender().name())))
                .andExpect(jsonPath("$.country.code", is(mockUser.getCountry().getCode())))
                .andExpect(jsonPath("$.birthdate").value(birthDate))
                .andExpect(jsonPath("$.phoneNumber", is(mockUser.getPhoneNumber())))
                .andDo(document("{method-name}",
                        requestFields(this.getUserFieldDescriptorGET()),
                        responseFields(this.getUserFieldDescriptorGET())));
    }


    @Test
    @DisplayName("relecture utilisateur Francais avec code du pays hors de l'utilisateur countryCode")
    public void findOneUserWithCountryCode() throws Exception {
        String birthDate = "2001-01-16";
        User mockReturnUser = new User();
        mockReturnUser.setBirthdate(LocalDate.parse(birthDate, formatter));
        mockReturnUser.setUserName("Hugo");
        mockReturnUser.setGender(Gender.M);
        mockReturnUser.setCountry(countryFR);
        mockReturnUser.setPhoneNumber("0601020304");

        User user = new User();
        user.setBirthdate(LocalDate.parse(birthDate, formatter));
        user.setUserName("Hugo");
        user.setGender(Gender.M);
        user.setPhoneNumber("0601020304");

        when(userService.findByUser(any(User.class))).thenReturn(mockReturnUser);

        mockMvc.perform(get("/api/user/find")
                .param("countryCode", countryFR.getCode())  //countryCode
                .param("userName", user.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.userName", is(mockReturnUser.getUserName())))
                .andExpect(jsonPath("$.gender", is(mockReturnUser.getGender().name())))
                .andExpect(jsonPath("$.country.code", is(mockReturnUser.getCountry().getCode())))
                .andExpect(jsonPath("$.phoneNumber", is(mockReturnUser.getPhoneNumber()))
                )
                .andDo(document("{method-name}", requestParameters(
                        parameterWithName("countryCode").description("CountryCode, only values in country table").optional(),
                        parameterWithName("phoneNumer").description("PhoneNumber").optional(),
                        parameterWithName("gender").description("Gender only values M or F").optional(),
                        parameterWithName("userName").description("The username")

                ), responseFields(this.getUserFieldDescriptorGET())));

    }


    private FieldDescriptor[] getUserFieldDescriptorPOSTResponseFULL() {
        return new FieldDescriptor[]{fieldWithPath("birthdate").description("The birth date of the user").type(LocalDate.class.getSimpleName()),
                fieldWithPath("userName").description("The name of the user").type(String.class.getSimpleName()),
                fieldWithPath("gender").description("The gender of the user (F or M)").optional().type(Gender.class.getSimpleName()),
                fieldWithPath("phoneNumber").description("The cell phone number of the user").optional().type(String.class.getSimpleName()),
                fieldWithPath("id").description("The unique id of the user").type(Long.class.getSimpleName()),
                fieldWithPath("country.id").description("The internal countryId of the user").type(Long.class.getSimpleName()),
                fieldWithPath("country.code").description("The countrycode of the user").type(String.class.getSimpleName()),
                fieldWithPath("country.name").description("The countryName of the user").type(String.class.getSimpleName()),
        };
    }


    private FieldDescriptor[] getUserFieldDescriptorPOSTRequestFULL() {
        return new FieldDescriptor[]{fieldWithPath("user.birthdate").description("The birth date of the user").type(LocalDate.class.getSimpleName()),
                fieldWithPath("countryCode").description("The code for external CountryCode is only for input and could replace user.country").type(String.class.getSimpleName()),
                fieldWithPath("user.userName").description("The name of the user").type(String.class.getSimpleName()),
                fieldWithPath("user.gender").description("The gender of the user (F or M)").optional().type(Gender.class.getSimpleName()),
                fieldWithPath("user.phoneNumber").description("The cell phone number of the user").optional().type(String.class.getSimpleName()),
                fieldWithPath("user.id").ignored(),
                fieldWithPath("user.country.id").description("The internal countryId of the user is used if countryCode is not provided").type(Long.class.getSimpleName()),
                fieldWithPath("user.country.code").optional().description("The countrycode of the user").optional().type(String.class.getSimpleName()),
                fieldWithPath("user.country.name").optional().description("The countryName of the user").optional().type(String.class.getSimpleName()),
        };
    }

    private FieldDescriptor[] getUserFieldDescriptorPOSTRequest() {
        return new FieldDescriptor[]{fieldWithPath("user.birthdate").description("The birth date of the user").type(LocalDate.class.getSimpleName()),
                fieldWithPath("countryCode").description("The code for external CountryCode is just for input").type(String.class.getSimpleName()),
                fieldWithPath("user.userName").description("The name of the user").type(String.class.getSimpleName()),
                fieldWithPath("user.gender").description("The gender of the user (F or M)").optional().type(Gender.class.getSimpleName()),
                fieldWithPath("user.phoneNumber").description("The cell phone number of the user").optional().type(String.class.getSimpleName()),
                fieldWithPath("user.id").ignored(),
                fieldWithPath("user.country").ignored().optional(),
        };
    }


    private FieldDescriptor[] getUserFieldDescriptorGET() {
        return new FieldDescriptor[]{fieldWithPath("birthdate").description("The birth date of the user").type(LocalDate.class.getSimpleName()),
                fieldWithPath("userName").description("The name of the user").type(String.class.getSimpleName()),
                fieldWithPath("gender").description("The gender of the user (F or M)").type(Gender.class.getSimpleName()),
                fieldWithPath("phoneNumber").description("The cell phone number of the user").type(String.class.getSimpleName()),
                fieldWithPath("id").description("The unique id of the user").optional().type(Long.class.getSimpleName()),
                fieldWithPath("country.id").description("The internal countryId of the user").type(Long.class.getSimpleName()),
                fieldWithPath("country.code").description("The countrycode of the user").type(String.class.getSimpleName()),
                fieldWithPath("country.name").description("The countryName of the user").type(String.class.getSimpleName()),
        };
    }


    private FieldDescriptor[] getUserFieldDescriptorGET2() {
        return new FieldDescriptor[]{fieldWithPath("birthdate").description("The birth date of the user").type(LocalDate.class.getSimpleName()),
                fieldWithPath("userName").description("The name of the user").type(String.class.getSimpleName()),
                fieldWithPath("gender").description("The gender of the user (F or M)").type(Gender.class.getSimpleName()),
                fieldWithPath("phoneNumber").description("The cell phone number of the user").type(String.class.getSimpleName()),
                fieldWithPath("id").description("The unique id of the user").optional().type(Long.class.getSimpleName()),
                fieldWithPath("country").ignored().optional(),
        };
    }

}
