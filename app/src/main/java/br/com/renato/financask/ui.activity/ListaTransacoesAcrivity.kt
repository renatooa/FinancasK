package br.com.renato.financask.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.renato.financask.R
import br.com.renato.financask.extension.formatarMoeda
import br.com.renato.financask.extension.setTextColorCompat
import br.com.renato.financask.model.Tipo.DESPESA
import br.com.renato.financask.model.Tipo.RECEITA
import br.com.renato.financask.model.Transacao
import br.com.renato.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.resumo_card.*
import java.math.BigDecimal

class ListaTransacoesAcrivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes);
        configurarCorTextResumo();
        configurarCorTextTotal()

        val transacoes: List<Transacao> = transacoesExemplos()

        atualizarResumo(transacoes)


        configurarLista(transacoes)
    }

    private fun configurarCorTextResumo() {
        resumo_card_despesa.setTextColorCompat(this, R.color.despesa);
        resumo_card_receita.setTextColorCompat(this, R.color.receita);
    }

    private fun configurarCorTextTotal(saldo: BigDecimal = BigDecimal(0)) {
        if (saldo < BigDecimal(0)) {
            resumo_card_total.setTextColorCompat(this, R.color.despesa);
        } else {
            resumo_card_total.setTextColorCompat(this, R.color.colorPrimary);
        }
    }

    private fun atualizarResumo(transacoes: List<Transacao>) {

        var despesas: BigDecimal = BigDecimal(0);
        var receitas: BigDecimal = BigDecimal(0);

        for (transacao in transacoes) {
            if (DESPESA == transacao.tipo) {
                despesas = despesas.plus(transacao.valor)
            } else {
                receitas = receitas.plus(transacao.valor)
            }
        }

        val saldo = receitas.subtract(despesas);

        popularDadosResumo(receitas, despesas, saldo)

        configurarCorTextTotal(saldo)
    }

    private fun popularDadosResumo(
        receitas: BigDecimal,
        despesas: BigDecimal,
        saldo: BigDecimal
    ) {
        resumo_card_receita.setText(receitas.formatarMoeda())
        resumo_card_despesa.setText(despesas.formatarMoeda())
        resumo_card_total.setText(saldo.formatarMoeda())
    }

    private fun configurarLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesExemplos(): List<Transacao> {
        val transacoes = listOf<Transacao>(
            Transacao(BigDecimal(20.50), "Comida"),
            Transacao(BigDecimal(50.00), RECEITA),
            Transacao(valor = BigDecimal(50.00), tipo = DESPESA),
            Transacao(categoria = "Economia", tipo = RECEITA, valor = BigDecimal(100))
        )
        return transacoes
    }
}