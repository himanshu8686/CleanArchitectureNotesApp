package com.cleanarchitecturenotesapp.feature_work_manager.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface FileApiService {

    /**
     * Downloads an image from the Unsplash API.
     *
     * @return Response containing the image data as ResponseBody
     */
    @GET("/photo-1500648767791-00dcc994a43e?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cGVyc29uYXxlbnwwfHwwfHx8MA%3D%3D")
    suspend fun downloadImage(): Response<ResponseBody>
}