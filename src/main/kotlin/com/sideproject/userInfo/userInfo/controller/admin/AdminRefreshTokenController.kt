package com.sideproject.userInfo.userInfo.controller.admin

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.service.admin.RefreshTokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/auth")
@Validated
class AdminRefreshTokenController(
    private val refreshTokenService: RefreshTokenService,
) {
    @PostMapping("/refresh")
    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<RestResponse<Map<String, String>>> {
        return ResponseEntity.ok(refreshTokenService.refreshToken(request))
    }
}