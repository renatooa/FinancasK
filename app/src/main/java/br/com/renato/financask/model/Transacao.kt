package br.com.renato.financask.model

import java.math.BigDecimal
import java.util.Calendar

class Transacao(
    valor: BigDecimal,
    var categoria: String,
    var tipo : Tipo = Tipo.DESPESA,
    var data: Calendar = Calendar.getInstance()
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