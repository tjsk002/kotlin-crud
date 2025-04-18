package com.sideproject.userInfo.userInfo.data.dto.users

import com.sideproject.userInfo.userInfo.data.entity.UserEntity
import java.time.LocalDateTime

class UserResponseDto(
    val content: List<UsersDto>,
    val pageInfo: PageInfoDto
)

class UsersDto(
    val userId: Long?,
    val username: String,
    val nickName: String,
    val gender: String,
    val isActive: Boolean,
    val type: String,
    val description: String?,
    val createdAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null
) {
    companion object {
        fun fromEntity(userEntity: UserEntity): UsersDto {
            return UsersDto(
                userEntity.id,
                userEntity.username,
                userEntity.nickName,
                userEntity.gender,
                userEntity.isActive,
                userEntity.type,
                userEntity.description,
                userEntity.createdAt,
                userEntity.deletedAt
            )
        }
    }
}

class PageInfoDto(
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int
)
