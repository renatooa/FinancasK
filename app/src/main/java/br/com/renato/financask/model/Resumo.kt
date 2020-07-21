package br.com.renato.financask.model

import java.math.BigDecimal

class Resumo(
    private val transacoes: List<Transacao>
) {
    /** Single expression Function
    fun somarReceitas() = somarTransacao(Tipo.RECEITA)**/
    val somarReceitas get() = somarTransacao(Tipo.RECEITA)
    val somarDespesas get() = somarTransacao(Tipo.DESPESA)
    val calcularTotal get() = somarReceitas.subtract(somarDespesas)

    private fun somarTransacao(tipo: Tipo): BigDecimal {

        val somaTransacoes: Double =
            transacoes
                .filter { it.tipo == tipo }
                .sumByDouble { it.valor.toDouble() }

        return BigDecimal(somaTransacoes)
    }
}