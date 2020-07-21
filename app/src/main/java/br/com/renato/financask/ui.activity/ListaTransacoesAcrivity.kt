package br.com.renato.financask.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.renato.financask.R
import br.com.renato.financask.delegade.TransacaoDelegate
import br.com.renato.financask.model.Tipo
import br.com.renato.financask.model.Transacao
import br.com.renato.financask.ui.ResumoView
import br.com.renato.financask.ui.adapter.ListaTransacoesAdapter
import br.com.renato.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesAcrivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes);

        atualizar()
        configurarFab()


    }

    private fun configurarFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            exibirAdicionaTransacaoDialogo(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            exibirAdicionaTransacaoDialogo(Tipo.DESPESA)
        }
    }

    private fun exibirAdicionaTransacaoDialogo(tipo: Tipo) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this).exibir(
            tipo,
            object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    addTransacao(transacao)
                }
            })
    }

    private fun addTransacao(transacao: Transacao) {
        this.transacoes.add(transacao)
        this.atualizar()
        lista_transacoes_adiciona_menu.close(true)
    }


    private fun atualizar() {
        configuraResumo()
        configurarLista()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(window.decorView, transacoes)
        resumoView.atualizar();
    }


    private fun configurarLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }
}