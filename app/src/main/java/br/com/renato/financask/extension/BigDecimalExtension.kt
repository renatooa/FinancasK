package br.com.renato.financask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.formatarMoeda() : String{
    val currencyInstance = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    return  currencyInstance.format(this);
}
