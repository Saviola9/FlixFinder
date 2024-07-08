package com.example.flixfinder.util

interface Mapper<Input, Output> {
    fun map(input: Input) : Output
}