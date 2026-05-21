package com.example.myapplication.data.network

import com.example.myapplication.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}