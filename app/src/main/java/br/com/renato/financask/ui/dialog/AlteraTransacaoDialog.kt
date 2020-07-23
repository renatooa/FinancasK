package br.com.renato.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.renato.financask.R
import br.com.renato.financask.model.Tipo
import br.com.renato.financask.model.Transacao
import java.util.*

class AlteraTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context,
    private val transacao: Transacao
) : FormularioTransacaoDialog(viewGroup, context) {

    override val tituloBotaoPositivo: String
        get() = context.getString(R.string.alterar)

    override fun tituloPorTipo(tipo: Tipo): Int {
        if (Tipo.RECEITA == tipo) {
            return R.string.altera_receita
        } else {
            return R.string.altera_despesa
        }
    }

    override fun configurarCategoria(tipo: Tipo) {
        super.configurarCategoria(transacao.tipo)

        with(categoriaSpinner) {
            this.adapter = adapter
            val categorias = categoriasPorTipo(tipo)
            val categoriasArray = context.resources.getStringArray(categorias);
            val posicaoEscolhida = categoriasArray.indexOf(transacao.categoria)
            this.setSelection(posicaoEscolhida, true)
        }
    }

    override fun configurarValor() {
        super.configurarValor()
        valorText.setText(transacao.valor.toString())
    }

    override fun configurarData(calendar: Calendar) {
        super.configurarData(transacao.data)
    }
}