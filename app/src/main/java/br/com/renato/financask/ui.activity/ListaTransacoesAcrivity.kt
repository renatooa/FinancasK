package br.com.renato.financask.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.renato.financask.R
import br.com.renato.financask.model.Tipo.DESPESA
import br.com.renato.financask.model.Tipo.RECEITA
import br.com.renato.financask.model.Transacao
import br.com.renato.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

class ListaTransacoesAcrivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes);

        val transacoes: List<Transacao> = transacoesExemplos()

        configurarLista(transacoes)
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