package com.exallium.swissrunner.core.entities

data class Tournament(val pk: Long, val name: String)
data class Player(val pk: Long, val name: String, val id: Long)
data class PlayerToTournamentLink(val pk: Long, val player: Player, val tournament: Tournament)
