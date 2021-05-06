package com.example.testkyrylohryzhuk.models

data class GoogleMapResponse(
    var routes: List<Routes>
)

data class Routes(
    var legs: List<Legs>
)

data class Legs(
    var steps: List<Steps>
)

data class Steps(
    var polyline: PolyLine
)

data class PolyLine(
    var points: String = ""
)