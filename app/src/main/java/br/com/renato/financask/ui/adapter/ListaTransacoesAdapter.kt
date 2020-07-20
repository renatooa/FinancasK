package br.com.renato.financask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import br.com.renato.financask.R
import br.com.renato.financask.extension.formatarData
import br.com.renato.financask.extension.formatarMoeda
import br.com.renato.financask.extension.limitaCaracteres
import br.com.renato.financask.model.Tipo
import br.com.renato.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(
    private val transacoes: List<Transacao>,
    private val context: Context
) : BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getView(posicao: Int, view: View?, viewGroup: ViewGroup?): View {

        val view = LayoutInflater.from(context).inflate(R.layout.transacao_item, viewGroup, false);

        val transacao = getItem(posicao);

        popularTransacao(view, transacao)

        return view
    }

    private fun popularTransacao(
        view: View,
        transacao: Transacao
    ) {
        view.transacao_valor.text = transacao.valor.formatarMoeda()
        view.transacao_categoria.setText(transacao.categoria.limitaCaracteres(limiteDaCategoria))
        view.transacao_data.text = transacao.data.formatarData()

        popularTipoTransacao(transacao, view)
    }

    private fun popularTipoTransacao(
        transacao: Transacao,
        view: View
    ) {
        when (transacao.tipo){
            Tipo.RECEITA->{
                modificarCorValor(view, R.color.receita)
                modifcarIcone(view, R.drawable.icone_transacao_item_receita)
            }
            Tipo.DESPESA->{
                modificarCorValor(view, R.color.despesa)
                modifcarIcone(view, R.drawable.icone_transacao_item_despesa)
            }
        }
    }

    private fun modifcarIcone(view: View, drawable: Int) {
        view.transacao_icone.setBackgroundResource(drawable)
    }

    private fun modificarCorValor(view: View, cor: Int) {
        view.transacao_valor.setTextColor(ContextCompat.getColor(context, cor))
    }

    override fun getItem(posicao: Int): Transacao {
        return transacoes[posicao];
    }

    override fun getItemId(posicao: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size;
    }
}