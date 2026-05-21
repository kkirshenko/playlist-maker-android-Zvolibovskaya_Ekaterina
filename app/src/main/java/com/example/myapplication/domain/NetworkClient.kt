package com.example.myapplication.domain

import com.example.myapplication.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}