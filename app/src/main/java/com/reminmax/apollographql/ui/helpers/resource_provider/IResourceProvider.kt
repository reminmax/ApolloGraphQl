package com.reminmax.apollographql.ui.helpers.resource_provider

interface IResourceProvider {
    fun getString(resourceId: Int): String
    fun getString(resourceId: Int, vararg args: Any): String
}