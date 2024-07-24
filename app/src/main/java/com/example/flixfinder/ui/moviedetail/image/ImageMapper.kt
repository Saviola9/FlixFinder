package com.example.flixfinder.ui.moviedetail.image

import com.example.flixfinder.data.remote.ImagesResponse
import com.example.flixfinder.util.Mapper
import com.example.flixfinder.util.toOriginalUrl
import javax.inject.Inject

class ImageMapper @Inject constructor() : Mapper<ImagesResponse, List<Image>> {
    override fun map(input: ImagesResponse): List<Image> {
        return buildList {
            addAll(input.backdrops.map { Image(it.filePath.toOriginalUrl(), it.voteCount) })
            addAll(input.posters.map { Image(it.filePath.toOriginalUrl(), it.voteCount) })
            sortByDescending { it.voteCount }
        }
    }
}