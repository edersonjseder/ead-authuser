package com.ead.authuser.dtos;

import com.ead.authuser.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends RepresentationModel<UserDto> {
    public interface UserView {
        interface RegistrationPost {}
        interface UserPut {}
        interface PasswordPut {}
        interface ImagePut {}
    }

    private UUID id;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, message = "Username is required")
    @Size(min = 4, max = 50, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @UsernameConstraint(groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String username;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class}, message = "Password is required")
    @Size(min = 6, max = 20, groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @NotBlank(groups = UserView.PasswordPut.class, message = "Old Password is required")
    @Size(min = 6, max = 20, groups = UserView.PasswordPut.class)
    @JsonView(UserView.PasswordPut.class)
    private String oldPassword;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, message = "E-mail is required")
    @Email(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, message = "Insert valid e-mail")
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String email;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, message = "Full name is required")
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @NotBlank(groups = UserView.RegistrationPost.class, message = "CPF is required")
    @JsonView(UserView.RegistrationPost.class)
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class, message = "Image URL is required")
    @JsonView(UserView.ImagePut.class)
    private String imageUrl;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String status;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String type;

    private String creationDate;
    private String lastUpdateDate;
    private String currentPasswordDate;

    @JsonIgnore
    private boolean isAuthorized;
}
