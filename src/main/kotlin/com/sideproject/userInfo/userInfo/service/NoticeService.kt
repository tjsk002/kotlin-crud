package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.response.ResponseUtils
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.notice.NoticeDto
import com.sideproject.userInfo.userInfo.data.dto.notice.NoticeResponseDto
import com.sideproject.userInfo.userInfo.data.dto.notice.PageInfoDto
import com.sideproject.userInfo.userInfo.data.dto.notice.ViewCountRequest
import com.sideproject.userInfo.userInfo.data.entity.NoticeEntity
import com.sideproject.userInfo.userInfo.repository.NoticeRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeService(
    private val noticeRepository: NoticeRepository
) {
    @Transactional(readOnly = true)
    fun getNoticeList(pageable: Pageable): RestResponse<Map<String, Any>> {
        val sortedPageable = PageRequest.of(
            pageable.pageNumber,
            pageable.pageSize,
            Sort.by(Sort.Direction.DESC, "createdAt")
        )

        val noticeListDto: Page<NoticeEntity> = noticeRepository.findAll(sortedPageable)
        val noticeContent: MutableList<NoticeDto> = noticeListDto.map {
            NoticeDto.fromEntity(it)
        }.toList()

        val pageInfo = PageInfoDto(
            noticeListDto.number,
            noticeListDto.size,
            noticeListDto.totalElements,
            noticeListDto.totalPages
        )

        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(NoticeResponseDto(noticeContent, pageInfo))
        )
    }

    @Transactional(readOnly = true)
    fun getNoticeDetail(noticeId: Long): RestResponse<Map<String, Any>> {
        val findNotice = noticeRepository.findById(noticeId)
        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(NoticeDto.fromEntity(findNotice.get()))
        )
    }

    fun increaseViewCount(noticeDto: ViewCountRequest): RestResponse<Map<String, Any>> {
        val notice = noticeRepository.findById(noticeDto.noticeId)
            .orElseThrow { IllegalArgumentException("공지 사항 글이 존재하지 않습니다.") }

        notice.viewCount += 1
        noticeRepository.save(notice)

        return RestResponse.success(
            ResponseUtils.messageAddMapOfParsing(notice)
        )
    }
}