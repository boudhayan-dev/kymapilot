package com.sap.clm.sl.cias.kyma.KymaPilot.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfo {

    private String username;
    private String email;
    private String tenantName;
    private String tenantGuid;
    private String token;
    private String firstName;
    private String lastName;
    private boolean isTechnicalUser;

}