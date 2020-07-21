package br.com.renato.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatarData(): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    return simpleDateFormat.format(time)
}