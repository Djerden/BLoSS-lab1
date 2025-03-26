package com.djeno.lab1.controllers;

import com.djeno.lab1.persistence.DTO.app.AppDetailsDto;
import com.djeno.lab1.persistence.DTO.app.AppListDto;
import com.djeno.lab1.persistence.DTO.app.CreateAppRequest;
import com.djeno.lab1.persistence.models.App;
import com.djeno.lab1.persistence.models.Purchase;
import com.djeno.lab1.persistence.models.User;
import com.djeno.lab1.services.AppService;
import com.djeno.lab1.services.PurchaseService;
import com.djeno.lab1.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/app")
@RestController
public class AppController {

    private final UserService userService;
    private final AppService appService;
    private final PurchaseService purchaseService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<String> publishApp(
            @RequestPart @Valid CreateAppRequest appData,
            @RequestPart(required = false) MultipartFile icon,
            @RequestPart MultipartFile file,
            @RequestPart(required = false) List<MultipartFile> screenshots) {

        appService.createApp(appData, icon, file, screenshots);
        return ResponseEntity.ok("Приложение загружено");
    }

    @GetMapping("/list")
    public ResponseEntity<Page<AppListDto>> getApps(
            @RequestParam(required = false) Long categoryId,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<AppListDto> apps = appService.getApps(categoryId, pageable);
        return ResponseEntity.ok(apps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppDetailsDto> getAppDetails(@PathVariable Long id) {
        AppDetailsDto appDetails = appService.getAppDetails(id);
        return ResponseEntity.ok(appDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'ADMIN')")
    public ResponseEntity<String> deleteApp(@PathVariable Long id) {
        appService.deleteApp(id);
        return ResponseEntity.ok("Приложение успешно удалено");
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadApp(@PathVariable Long id) {
        return appService.downloadApp(id);
    }

    @PostMapping("/purchase/{id}")
    public ResponseEntity<String> purchaseApp(@PathVariable Long id) {
        App app = appService.getAppById(id);
        User user = userService.getCurrentUser();
        Purchase purchase = purchaseService.purchaseApp(app, user);
        return ResponseEntity.ok("Приложение было успешно оплачено");
    }

}
