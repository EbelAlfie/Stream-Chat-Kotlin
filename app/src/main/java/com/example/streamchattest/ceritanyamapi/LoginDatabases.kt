package com.example.streamchattest.ceritanyamapi

import com.example.streamchattest.presentation.login.model.Priviledge.Admin
import com.example.streamchattest.presentation.login.model.Priviledge.Customer
import com.example.streamchattest.presentation.login.model.UserModel

object LoginDatabases {

  val customers = listOf(
    UserModel(
      Customer, "cacing perut", "nematoda", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoibmVtYXRvZGEifQ.rei8TpTzHlA-Qsm1l3DVEe_pQiE-8qzhkAhH-QEf3EI"
    ),
    UserModel(
      Customer, "caching tanah", "platyheminthes", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoicGxhdHloZW1pbnRoZXMifQ.1VMjMp8EvtAJ0pkt7rXctr_ipGFQ6dwQMwcoMLHSqZk"
    ),
    UserModel(
      Admin, "Alfamart Jupiter", "k696", "k696"
    ),
    UserModel(
      Admin, "Alfamart Antartika", "k446", "k446"
    )
  )

}