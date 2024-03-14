package org.example.userservice.serviceImp;


import lombok.extern.slf4j.Slf4j;
import org.example.base.enums.RoleEnum;
import org.example.base.enums.UserStatusEnum;
import org.example.base.exceptions.DocumentorException;
import org.example.base.exceptions.DocumentorValidationException;
import org.example.base.utils.CommonUtils;
import org.example.base.utils.CommonValidations;
import org.example.kafka.producer.NotificationProducer;
import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.responseDto.UserResponseDTO;
import org.example.userservice.model.UserDetails;
import org.example.userservice.repository.UserDetailsRepository;
import org.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.*;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    NotificationProducer notificationProducer;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    //TODO:: Make it configurable
    Executor executor = Executors.newFixedThreadPool(8);

    @Override
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        UserDetails userDetails = null;
        try {
            userDetails = processUserRequestAsync(userRequestDTO).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getCause());
        } catch (ExecutionException e) {
            if (e.getCause() instanceof DocumentorValidationException) {
                log.info("Exception occurred: {}", e.getMessage());
                throw new DocumentorValidationException(e.getCause().getMessage());
            }
            log.info("Exception occurred: {}", e.getMessage());
            throw new DocumentorException(e.getCause().getMessage());
        }
        return CommonUtils.convert(userDetails, UserResponseDTO.class);
    }

    public CompletableFuture<UserDetails> processUserRequestAsync(UserRequestDTO userRequestDTO) {
        try {
            return validateUserNameAndEnumAsync(userRequestDTO)
                    .thenCompose(validated -> saveToDbAsync(userRequestDTO));
        } catch (RuntimeException e) {
            throw new CompletionException(e);
        }
    }

    private CompletableFuture<Boolean> validateUserNameAndEnumAsync(UserRequestDTO userRequestDTO) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return validateUserName(userRequestDTO) && CommonValidations.validateEnum(userRequestDTO.getRole(), RoleEnum.class);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    private CompletableFuture<UserDetails> saveToDbAsync(UserRequestDTO userRequestDTO) {
        return CompletableFuture.supplyAsync(() -> {
            CompletableFuture<UserDetails> exceptionallyCompleted = new CompletableFuture<>();
            try {
                log.info("Saving user info into user db");
                return saveDetailsToDB(userRequestDTO);
            } catch (DocumentorException e) {
                log.info("Exception occurred while saving user data into DB, username: {}", userRequestDTO.getUserName());
                exceptionallyCompleted.completeExceptionally(new DocumentorException(e.getMessage()));
                return exceptionallyCompleted.join();  // Wait for the exceptionallyCompleted future to complete exceptionally
            }
        }, executor);
    }


    private Boolean validateUserName(UserRequestDTO userRequestDTO) {
        if (CommonUtils.isValidString(userRequestDTO.getUserName())
                && CommonUtils.isValidString(userRequestDTO.getName())
                && CommonUtils.isValidString(userRequestDTO.getLastName())) {
            return true;
        }
        throw new DocumentorValidationException("Not a valid userName present");
    }


    private UserDetails saveDetailsToDB(UserRequestDTO userRequestDTO) {
        UserDetails userDetails = null;
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(userRequestDTO.getPassword());
            userDetails = new UserDetails();
            userDetails.setUserName(userRequestDTO.getUserName());
            userDetails.setName(userRequestDTO.getName());
            userDetails.setLastName(userRequestDTO.getLastName());
            userDetails.setRole(RoleEnum.valueOf(userRequestDTO.getRole()));
            userDetails.setMobileNo(userRequestDTO.getMobileNo());
            userDetails.setEmailId(userRequestDTO.getEmailId());
            userDetails.setUserStatusEnum(Objects.isNull(userRequestDTO.getUserStatusEnum())
                    ? UserStatusEnum.ACTIVE
                    : UserStatusEnum.valueOf(userRequestDTO.getUserStatusEnum()));
            userDetails.setCreatedAt(LocalDateTime.now());
            userDetails.setModifiedAt(null);
            userDetails.setPassword(encodedPassword);
            userDetailsRepository.save(userDetails);
        } catch (DataIntegrityViolationException e) {
            throw new DocumentorException(e.getCause().getLocalizedMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getCause().getLocalizedMessage());
        }
        return userDetails;
    }
}

