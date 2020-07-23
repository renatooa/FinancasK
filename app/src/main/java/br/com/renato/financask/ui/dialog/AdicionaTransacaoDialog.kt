package br.com.renato.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.renato.financask.R
import br.com.renato.financask.model.Tipo

class AdicionaTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) : FormularioTransacaoDialog(viewGroup, context) {

    override val tituloBotaoPositivo: String
        get() = context.getString(R.string.adicionar)

    override fun tituloPorTipo(tipo: Tipo): Int {
        if (Tipo.RECEITA == tipo) {
            return R.string.adiciona_receita
        } else {
            return R.string.adiciona_despesa
        }
    }
}