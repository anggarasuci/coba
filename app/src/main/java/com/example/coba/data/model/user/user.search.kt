package com.example.coba.data.model.user

data class RequestUserSearch(
    val keyword: String = "",
    val pages: Int = 0,
    val rows: Int = 0
)

data class ResponseUserSearch(
    val totalCount: Int = 0,
    val incompleteResults: Boolean = false,
    val items: List<User> = listOf()
)

data class User(
    val gistsUrl: String = "",
    val reposUrl: String = "",
    val followingUrl: String = "",
    val starredUrl: String = "",
    val login: String = "",
    val followersUrl: String = "",
    val type: String = "",
    val url: String = "",
    val subscriptionsUrl: String = "",
    val score: Int = 0,
    val receivedEventsUrl: String = "",
    val avatarUrl: String = "",
    val eventsUrl: String = "",
    val htmlUrl: String = "",
    val siteAdmin: Boolean = false,
    val id: Int = 0,
    val gravatarId: String = "",
    val nodeId: String = "",
    val organizationsUrl: String = ""
)