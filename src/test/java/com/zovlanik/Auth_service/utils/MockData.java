package com.zovlanik.Auth_service.utils;

import com.example.common.AddressDto;
import com.example.common.IndividualDto;
import com.example.common.UserDto;
import com.zovlanik.Auth_service.dto.KeyCloakUserDto;
import com.zovlanik.Auth_service.dto.RegistrationDto;

import java.time.LocalDateTime;

public class MockData {
    public static RegistrationDto getRegistrationDto(){
        RegistrationDto registrationDto = new RegistrationDto();

        KeyCloakUserDto keyCloakUserDto = KeyCloakUserDto.builder()
                .email("test2@mail.ru")
                .username("usertest2")
                .firstName("usertest2_first_name")
                .lastName("usertest2_last_name")
                .password("usertest2")
                .confirmPassword("usertest2")
                .build();
        registrationDto.setKeyCloakUserDto(keyCloakUserDto);


        AddressDto addressDto = new AddressDto();
        addressDto.setCreated(LocalDateTime.parse("2024-03-06T09:22:34.423"));
        addressDto.setUpdated(LocalDateTime.parse("2024-03-06T09:22:34.423"));
        addressDto.setCountryId(2);
        addressDto.setAddress("Address of usertest2");
        addressDto.setZipCode("223456");
        addressDto.setCity("City of usertest2");
        addressDto.setState("State of usertest2");
        addressDto.setArchived(null);
        registrationDto.setAddressDto(addressDto);


        UserDto userDto = new UserDto();
        userDto.setSecretKey("dont know why");
        userDto.setCreated(LocalDateTime.parse("2024-03-06T09:22:34.423"));
        userDto.setUpdated(null);
        userDto.setFirstName("usertest2_first_name");
        userDto.setLastName("usertest2_last_name");
        userDto.setVerifiedAt(null);
        userDto.setArchivedAt(null);
        userDto.setStatus("ACTIVE");
        userDto.setFilled(false);
        userDto.setAddressId(null);
        registrationDto.setUserDto(userDto);


        IndividualDto individualDto = new IndividualDto();
        individualDto.setUserId(null);
        individualDto.setCreated(LocalDateTime.parse("2024-03-06T09:22:34.423"));
        individualDto.setUpdated(null);
        individualDto.setPassportNumber("22 34 5678");
        individualDto.setPhoneNumber("+7 909 223 45 67");
        individualDto.setEmail("test2@mail.ru");
        individualDto.setVerifiedAt(null);
        individualDto.setArchivedAt(null);
        individualDto.setStatus("ACTIVE");
        registrationDto.setIndividualDto(individualDto);
        return registrationDto;
    }
}
