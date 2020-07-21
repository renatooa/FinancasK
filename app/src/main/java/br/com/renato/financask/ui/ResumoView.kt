package br.com.renato.financask.ui

import android.view.View
import br.com.renato.financask.R
import br.com.renato.financask.extension.formatarMoeda
import br.com.renato.financask.extension.setTextColorCompat
import br.com.renato.financask.model.Tipo
import br.com.renato.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val view: View, private val transacoes: List<Transacao>) {

    fun atualizar(transacoes: List<Transacao>) {

        var despesas: BigDecimal = BigDecimal(0);
        var receitas: BigDecimal = BigDecimal(0);

        for (transacao in transacoes) {
            if (Tipo.DESPESA == transacao.tipo) {
                despesas = despesas.plus(transacao.valor)
            } else {
                receitas = receitas.plus(transacao.valor)
            }
        }

        val saldo = receitas.subtract(despesas);
        popularDados(receitas, despesas, saldo)
        configurarCorTextTotal(saldo)
    }

    fun popularDados(
        receitas: BigDecimal,
        despesas: BigDecimal,
        saldo: BigDecimal
    ) {
        view.resumo_card_receita.setText(receitas.formatarMoeda())
        view.resumo_card_despesa.setText(despesas.formatarMoeda())
        view.resumo_card_total.setText(saldo.formatarMoeda())
    }

    fun configurarCorText() {
        view.resumo_card_despesa.setTextColorCompat(view.context, R.color.despesa);
        view.resumo_card_receita.setTextColorCompat(view.context, R.color.receita);
    }

    fun configurarCorTextTotal(saldo: BigDecimal = BigDecimal(0)) {
        if (saldo < BigDecimal(0)) {
            view.resumo_card_total.setTextColorCompat(view.context, R.color.despesa);
        } else {
            view.resumo_card_total.setTextColorCompat(view.context, R.color.colorPrimary);
        }
    }
}