package br.com.renato.financask.model

import java.math.BigDecimal
import java.util.Calendar

class Transacao(
    valor: BigDecimal,
    val categoria: String,
    val tipo : Tipo = Tipo.DESPESA,
    val data: Calendar = Calendar.getInstance()
) {
    constructor(valor: BigDecimal, tipo : Tipo):this(valor, "Indefinida", tipo)

    var valor: BigDecimal = valor
        get() {
            return field
        }
        set(value) {
            field = value
        }
}