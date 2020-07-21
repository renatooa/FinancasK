package br.com.renato.financask.ui

import android.view.View
import android.widget.TextView
import br.com.renato.financask.R
import br.com.renato.financask.extension.formatarMoeda
import br.com.renato.financask.extension.setTextColorCompat
import br.com.renato.financask.model.Resumo
import br.com.renato.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(
    private val view: View,
    transacoes: List<Transacao>
) {

    private val resumo: Resumo = Resumo(transacoes)

    fun atualizar() {
        atualizarReceita()
        atualizarDespesas()
        atualizarSaldo()
    }

    private fun atualizarSaldo() {

        val saldo: BigDecimal = resumo.calcularTotal

        with(view.resumo_card_total) {
            text = saldo.formatarMoeda()
            setTextColorCompat(view.context, corSaldo(saldo))
        }
    }

    private fun atualizarDespesas() {
        with(view.resumo_card_despesa) {
            text = resumo.somarDespesas.formatarMoeda()
            setTextColorCompat(view.context, R.color.despesa)
        }
    }

    private fun atualizarReceita() {
        with(view.resumo_card_receita) {
            text = resumo.somarReceitas.formatarMoeda()
            setTextColorCompat(view.context, R.color.receita)
        }
    }

    private fun TextView.corSaldo(saldo: BigDecimal): Int {
        if (saldo >= BigDecimal(0)) {
            return R.color.receita
        } else {
            return R.color.despesa
        }
    }
}