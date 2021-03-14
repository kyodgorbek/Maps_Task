package com.example.maps_task.repository.exceptions

import com.example.maps_task.api.PlacesToVisitApi
import com.example.maps_task.model.GeoLocationDetailsModel
import com.example.maps_task.model.GeoSearchModel
import com.example.maps_task.model.Routes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import javax.inject.Inject

class PlacesToVisitRepositoryImpl @Inject constructor(
        private val placesToVisitApi: PlacesToVisitApi,
        private val gson: Gson
) : PlacesToVisitRepository {

    override fun getPointsOfInteresets(latitude: Double, longitude: Double): Single<List<GeoSearchModel>> {
        return placesToVisitApi.getPointsOfInteresets("${latitude}|${longitude}")
                .mapBodyMessages(gson)
                .mapNetworkErrors(gson)
                .map { response ->
                    val json = response.get("query")?.asJsonObject?.get("geosearch")
                    val type = object : TypeToken<List<GeoSearchModel>>() {}.type
                    gson.fromJson(json, type) as List<GeoSearchModel>
                }
    }

    override fun getPointsOfInteresetsDetails(pageId: Int): Single<GeoLocationDetailsModel> {
        return placesToVisitApi.getPointsOfInteresetsDetails(pageId)
                .mapBodyMessages(gson)
                .mapNetworkErrors(gson)
                .map { response ->
                    response
                }
    }

    override fun getDirections(url: String): Single<Routes> {
        return placesToVisitApi.getDirections(url)
                .mapBodyMessages(gson)
                .mapNetworkErrors(gson)
                .map { response ->
                    gson.fromJson(response, Routes::class.java)
                }
    }

}

interface PlacesToVisitRepository {
    fun getPointsOfInteresets(latitude: Double, longitude: Double): Single<List<GeoSearchModel>>
    fun getPointsOfInteresetsDetails(pageId: Int): Single<GeoLocationDetailsModel>
    fun getDirections(url: String): Single<Routes>
}
