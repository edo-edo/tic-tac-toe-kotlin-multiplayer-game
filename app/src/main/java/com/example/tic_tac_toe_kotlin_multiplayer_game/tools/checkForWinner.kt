package com.example.tic_tac_toe_kotlin_multiplayer_game.tools

fun checkForWinner(checkButtonList: MutableList<MutableList<String>>): String {

    var winner = " "

    for (i in 0..2) {
        if (
            (checkButtonList[i][0] == checkButtonList[i][1]) &&
            (checkButtonList[i][0] == checkButtonList[i][2]) &&
            (checkButtonList[i][0] != " ")
        ) winner = checkButtonList[i][0]
    }

    for (i in 0..2) {
        if (
            (checkButtonList[0][i] == checkButtonList[1][i]) &&
            (checkButtonList[0][i] == checkButtonList[2][i]) &&
            (checkButtonList[0][i] != " ")
        ) winner = checkButtonList[0][i]
    }

    if (
        (checkButtonList[0][0] == checkButtonList[1][1]) &&
        (checkButtonList[0][0] == checkButtonList[2][2]) &&
        (checkButtonList[0][0] != " ")
    ) winner = checkButtonList[0][0]

    if (
        (checkButtonList[0][2] == checkButtonList[1][1]) &&
        (checkButtonList[0][2] == checkButtonList[2][0]) &&
        (checkButtonList[0][2] != " ")
    ) winner = checkButtonList[0][2]

    var openSpots = 0
    for (i in 0..2) {
        for (j in 0..2) {
            if (checkButtonList[i][j] == " ") {
                openSpots++
            }
        }
    }
    return if (winner == " " && openSpots == 0) {
        "tie"
    } else {
        winner
    }
}