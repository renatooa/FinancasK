package br.com.renato.financask.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.renato.financask.R
import br.com.renato.financask.dao.TransacaoDao
import br.com.renato.financask.model.Tipo
import br.com.renato.financask.model.Transacao
import br.com.renato.financask.ui.ResumoView
import br.com.renato.financask.ui.adapter.ListaTransacoesAdapter
import br.com.renato.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.renato.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesAcrivity : AppCompatActivity() {

    private val transacaoDao : TransacaoDao = TransacaoDao()

    private val transacoes : List<Transacao> by lazy {
        transacaoDao.transacoes
    }

    private val viewActivity : View by lazy {
        window.decorView
    }

    private val viewGroupActivity : ViewGroup by lazy {
        viewActivity as ViewGroup
    }

    private val resumoView : ResumoView by lazy {
        ResumoView(viewActivity, transacoes)
    }

    override fun onCreate(savedInstanceState : Bundle?) {
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

    private fun exibirAdicionaTransacaoDialogo(tipo : Tipo) {
        AdicionaTransacaoDialog(viewGroupActivity, this)
            .exibir(tipo)
            { transacaoDelegada ->
                inserir(transacaoDelegada)
            }
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

        with(lista_transacoes_listview) {

            setOnItemClickListener { adapterView, _, posicao, _ ->
                val transacao : Transacao = adapterView.getItemAtPosition(posicao) as Transacao
                exibirAlteraTransacaoDialogo(posicao, transacao)
            }

            setOnCreateContextMenuListener({ menu, viee, menuInfo ->
                menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.remover))
            })
        }
    }

    override fun onContextItemSelected(item : MenuItem) : Boolean {

        val idMenu = item.itemId
        val infoMenu = item.menuInfo as AdapterView.AdapterContextMenuInfo

        if (idMenu == 1) {
            remover(infoMenu.position)
        }
        return super.onContextItemSelected(item)
    }

    private fun exibirAlteraTransacaoDialogo(posicao : Int, transacao : Transacao) {
        AlteraTransacaoDialog(viewGroupActivity, this, transacao).exibir(
            transacao.tipo,
            delegate =
            { transacaoDelegada ->
                alterar(posicao, transacaoDelegada)
            })
    }

    private fun remover(posicao : Int) {
        transacaoDao.remover(posicao)
        atualizar()
    }

    private fun alterar(
        posicao : Int,
        transacaoDelegada : Transacao
    ) {
        transacaoDao.alterar(posicao, transacaoDelegada)
        atualizar()
    }

    private fun inserir(transacao : Transacao) {
        transacaoDao.inserir(transacao)
        this.atualizar()
        lista_transacoes_adiciona_menu.close(true)
    }
}