package com.mcm.EmployeeManagementSystem.usecase.registrationrequest;

import com.google.zxing.WriterException;
import com.mcm.EmployeeManagementSystem.constant.EmailConstant;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.ActivationLink;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequestStatus;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.DataDecryptor;
import com.mcm.EmployeeManagementSystem.security.crypto.DataEncryptor;
import com.mcm.EmployeeManagementSystem.security.twofa.SecretKeyGenerator;
import com.mcm.EmployeeManagementSystem.store.ActivationLinkStore;
import com.mcm.EmployeeManagementSystem.store.AddressStore;
import com.mcm.EmployeeManagementSystem.store.RegistrationRequestStore;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.usecase.email.SendEmailUseCase;
import com.mcm.EmployeeManagementSystem.usecase.link.GenerateActivationLinkUseCase;
import com.mcm.EmployeeManagementSystem.usecase.qrcode.GenerateQrCodeUseCase;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.registrationrequest.ChangeRequestStatusValidator;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApproveRequestUseCase {
    private final RegistrationRequestStore store;
    private final UserStore userStore;
    private final ChangeRequestStatusValidator validator;
    private final AddressStore addressStore;
    private final GenerateActivationLinkUseCase generateActivationLinkUseCase;
    private final SendEmailUseCase sendEmailUseCase;
    private final ActivationLinkStore activationLinkStore;
    private final SecretKeyGenerator secretKeyGenerator;
    private final DataEncryptor dataEncryptor;
    private final GenerateQrCodeUseCase generateQrCodeUseCase;
    private final DataDecryptor dataDecryptor;


    public Response approve(Long requestId) throws NoSuchAlgorithmException, InvalidKeyException {
        ValidationReport report = validator.validate(requestId);
        if (report.isValid()) {
            RegistrationRequest request = store.find(requestId);
            request.setStatus(RegistrationRequestStatus.ACCEPTED);
            store.save(request);
            User user = toUser(request);
            User savedUser = userStore.save(user);
            String message = generateActivationLinkUseCase.generateActivationLink(savedUser.getId().toString());
            sendEmailUseCase.send(user.getEmail(), message, EmailConstant.ACTIVATION_SUBJECT);
            ActivationLink activationLink = new ActivationLink(0L, message, false);
            activationLinkStore.save(activationLink);
        }
        return new Response(report, requestId);
    }

    private User toUser(RegistrationRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setAddress(addressStore.find(request.getAddress().getId()));
        user.setPhoneNum(request.getPhoneNum());
        user.setTitle(request.getTitle());
        List<String> roleNames = new ArrayList<>();
        roleNames.add(request.getRoleName());
        user.setRoleNames(roleNames);
        user.setSecretKey(dataEncryptor.encryptData(secretKeyGenerator.generateSecretKey()));
        user.setStartOfWork(null);
        user.setAccountEnabled(false);
        return user;
    }
}
