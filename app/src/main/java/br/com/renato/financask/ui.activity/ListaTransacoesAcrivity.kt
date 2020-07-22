package br.com.renato.financask.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.renato.financask.R
import br.com.renato.financask.delegade.TransacaoDelegate
import br.com.renato.financask.model.Tipo
import br.com.renato.financask.model.Transacao
import br.com.renato.financask.ui.ResumoView
import br.com.renato.financask.ui.adapter.ListaTransacoesAdapter
import br.com.renato.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.renato.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesAcrivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()
    private lateinit var viewActivity: View
    private lateinit var resumoView: ResumoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes);
        viewActivity = window.decorView
        resumoView = ResumoView(viewActivity, transacoes)

        configurarLista()
        resumoView.atualizar()
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
        AdicionaTransacaoDialog(viewActivity as ViewGroup, this)
            .exibir(tipo,
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
        resumoView.atualizar()
        val listaTransacoesAdapter = lista_transacoes_listview.adapter as ListaTransacoesAdapter
        listaTransacoesAdapter.notifyDataSetChanged()
    }

    private fun configurarLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)

        configurarListenerListView();
    }

    private fun configurarListenerListView() {
        lista_transacoes_listview.setOnItemClickListener { adapterView, _, posicao, _ ->
            val transacao: Transacao = adapterView.getItemAtPosition(posicao) as Transacao
            exibirAlteraTransacaoDialogo(transacao)
        }
    }

    private fun exibirAlteraTransacaoDialogo(transacao: Transacao) {
        AlteraTransacaoDialog(viewActivity as ViewGroup, this, transacao).exibir(object :
            TransacaoDelegate {
            override fun delegate(transacao: Transacao) {
                atualizar()
            }
        })
    }
}