package com.bank.user.controller;

import com.bank.common.lib.exception.CommonCustomException;
import com.bank.common.lib.model.response.CommonSuccessResponse;
import com.bank.common.lib.utils.Constants;
import com.bank.common.lib.utils.MetadataContext;
import com.bank.user.dto.UserRegisterDto;
import com.bank.user.entity.User;
import com.bank.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bank/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CommonSuccessResponse<User>> createUser(@RequestBody UserRegisterDto userRegisterDto){
        User user = userService.createUser(userRegisterDto);
        return getSpecificResponse("New user register successfully", Constants.CREATED_STATUS_CODE, user);
    }

    @GetMapping("/get")
    public ResponseEntity<CommonSuccessResponse<List<User>>> getAllUser(){
        List<User> users = userService.getAllUser();
        return getSpecificResponse("All user fetched successfully", Constants.OK_STATUS_CODE, users);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus(){
        try{
            Map<String, Object> status = new LinkedHashMap<>();
            status.put("timestamp", LocalDateTime.now().toString());
            status.put("service", "user-service");
            status.put("status", "UP");
            status.put("components", Map.of(
                    "tokenService", testTokenService() ? "UP" : "DOWN"
            ));
            status.put("uptime", getUptime());
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean testTokenService() {
        try {
//            String dummyToken = tokenService.generateToken("health-check");
//            return tokenService.verifyToken(dummyToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getUptime() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        long uptimeMillis = runtimeMxBean.getUptime();

        Duration duration = Duration.ofMillis(uptimeMillis);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02dh:%02dm:%02ds", hours, minutes, seconds);
    }

    private <T> ResponseEntity<CommonSuccessResponse<T>> getSpecificResponse(String msg, int statusCode, T payload) {
        try {
            CommonSuccessResponse<T> response = CommonSuccessResponse.<T>builder()
                    .timestamp(String.valueOf(LocalDateTime.now()))
                    .status(Constants.SUCCESS_TAG)
                    .statusCode(statusCode)
                    .message(msg)
                    .metadata(MetadataContext.getMetadata())
                    .payload(payload)
                    .build();
            return ResponseEntity.status(statusCode).body(response);
        } catch (Exception e) {
            throw new CommonCustomException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
    }
}
