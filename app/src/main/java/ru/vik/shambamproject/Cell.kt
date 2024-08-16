package ru.vik.shambamproject

sealed class Cell {
    object Alive : Cell() {
        override fun toString(): String = "Живая"
    }
    object Dead : Cell() {
        override fun toString(): String = "Мёртвая"
    }
    object EarlyBorn : Cell() {
        override fun toString(): String = "Живая"
    }
    object EarlyDead : Cell() {
        override fun toString(): String = "Мёртвая"
    }
}