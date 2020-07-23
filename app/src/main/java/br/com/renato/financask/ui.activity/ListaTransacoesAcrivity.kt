package br.com.renato.financask.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.renato.financask.R
import br.com.renato.financask.model.Tipo
import br.com.renato.financask.model.Transacao
import br.com.renato.financask.ui.ResumoView
import br.com.renato.financask.ui.adapter.ListaTransacoesAdapter
import br.com.renato.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.renato.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesAcrivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    private val viewActivity: View by lazy {
        window.decorView
    }

    private val viewGroupActivity: ViewGroup by lazy {
        viewActivity as ViewGroup
    }

    private val resumoView: ResumoView by lazy {
        ResumoView(viewActivity, transacoes)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes);

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
        AdicionaTransacaoDialog(viewGroupActivity, this)
            .exibir(tipo)
            { transacaoDelegada ->
                addTransacao(transacaoDelegada)
            }
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
            exibirAlteraTransacaoDialogo(posicao, transacao)
        }
    }

    private fun exibirAlteraTransacaoDialogo(posicao: Int, transacao: Transacao) {
        AlteraTransacaoDialog(viewGroupActivity, this, transacao).exibir(
            transacao.tipo,
            delegate =
            { transacaoDelegada ->
                transacoes[posicao] = transacaoDelegada
                atualizar()
            })
    }
}