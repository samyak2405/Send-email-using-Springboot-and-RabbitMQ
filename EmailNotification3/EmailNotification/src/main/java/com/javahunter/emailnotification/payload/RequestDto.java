package com.javahunter.emailnotification.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
