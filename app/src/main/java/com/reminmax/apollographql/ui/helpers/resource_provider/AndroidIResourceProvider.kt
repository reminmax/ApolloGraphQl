package com.reminmax.apollographql.ui.helpers.resource_provider

import android.content.Context

class AndroidIResourceProvider(
    private val context: Context
): IResourceProvider {

    override fun getString(resourceId: Int): String = context.getString(resourceId)

    override fun getString(
        resourceId: Int,
        vararg args: Any
    ): String {
        return if (args.isNotEmpty()) {
            context.resources.getString(resourceId, *args)
        } else {
            context.resources.getString(resourceId)
        }
    }
}