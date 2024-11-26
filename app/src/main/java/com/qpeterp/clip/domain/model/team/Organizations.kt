package com.qpeterp.clip.domain.model.team

data class Organizations(
    val id: Int,
    val name: String,
    val inviteCode: String,
    val createdAt: String,
    val members: List<Int>
)