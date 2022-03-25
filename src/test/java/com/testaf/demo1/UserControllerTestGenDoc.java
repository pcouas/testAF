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

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
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
        countryFR = new Country();
        countryFR.setId(1L);
        countryFR.setCode("FR");
        countryFR.setName("FRANCE");

    }

    /**
     *
     * @throws Exception
     *
     * Creation
     */
    @Test
    @DisplayName("creation utilisateur Francais")
    public void testCreateOneUser() throws Exception {
        User user = new User();
        user.setBirthdate(LocalDate.parse("16/01/2001", formatter));
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
                .andExpect(jsonPath("$.country").value(countryFR))
            //    .andExpect(jsonPath("$.country.code", is( user.getCountry().getCode())))
           //     .andExpect(jsonPath("$.country.name", is( user.getCountry().getName())))
                .andExpect(jsonPath("$.phoneNumber").value("0601020304"))
                .andExpect(jsonPath("$.birthdate").value("2001-01-16"))
                .andDo(document("shouldCreateUser",
                        requestFields(getUserFieldDescriptorPOSTRequest()),
                        responseFields(getUserFieldDescriptorPOSTResponse())));

    }

    /**
     *
     * @throws Exception
     */
    @Test
    @DisplayName("relecture utilisateur Francais")
    public void testGetOneUser() throws Exception {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setBirthdate(LocalDate.parse("16/01/2001", formatter));
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
                .andExpect(jsonPath("$.userName", is( mockUser.getUserName())))
                .andExpect(jsonPath("$.gender", is( mockUser.getGender().name())))
                .andExpect(jsonPath("$.country.code", is( mockUser.getCountry().getCode())))
                .andDo(document("find",
                 requestFields(this.getUserFieldDescriptorGET()),
                 responseFields(this.getUserFieldDescriptorGET())));
    }


    private FieldDescriptor[] getUserFieldDescriptorPOSTResponse() {
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


    private FieldDescriptor[] getUserFieldDescriptorPOSTRequest() {
        return new FieldDescriptor[]{fieldWithPath("user.birthdate").description("The birth date of the user").type(LocalDate.class.getSimpleName()),
                fieldWithPath("countryCode").description("The code for external CountryCode is just for input").type(String.class.getSimpleName()),
                fieldWithPath("user.userName").description("The name of the user").type(String.class.getSimpleName()),
                fieldWithPath("user.gender").description("The gender of the user (F or M)").type(Gender.class.getSimpleName()),
                fieldWithPath("user.phoneNumber").description("The cell phone number of the user").type(String.class.getSimpleName()),
                fieldWithPath("id").description("The unique id of the user").optional().type(Long.class.getSimpleName()),
                fieldWithPath("user.id").description("The unique id of the user").optional().type(Long.class.getSimpleName()),
                fieldWithPath("user.country.id").description("The internal countryId of the user").type(Long.class.getSimpleName()),
                fieldWithPath("user.country.code").description("The countrycode of the user").type(String.class.getSimpleName()),
                fieldWithPath("user.country.name").description("The countryName of the user").type(String.class.getSimpleName()),
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


}
