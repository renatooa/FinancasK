package br.com.renato.financask.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.renato.financask.R
import br.com.renato.financask.extension.formatarData
import br.com.renato.financask.extension.setDDMMYYYY
import br.com.renato.financask.model.Tipo
import br.com.renato.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    protected val viewCriada = criarView()
    protected val categoriaSpinner = viewCriada.form_transacao_categoria
    protected val valorText = viewCriada.form_transacao_valor
    protected val dataText = viewCriada.form_transacao_data

    protected abstract val tituloBotaoPositivo: String

    open fun exibir(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {

        configurarData()
        configurarValor()
        configurarCategoria(tipo)
        configurarFormulario(
            tipo,
            delegate
        )
    }

    private fun configurarFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo = tituloPorTipo(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(
                tituloBotaoPositivo,
                { alerta, opcao ->

                    try {
                        val transacao = criarTransacao(tipo)

                        delegate(transacao)
                    } catch (ex: Exception) {
                        exibirErroRegistroTransacao(ex)
                    }
                })
            .setNegativeButton(
                context.getString(R.string.cancelar),
                { alerta, opcao ->
                    alerta.dismiss()
                })
            .show()
    }

    protected abstract fun tituloPorTipo(tipo: Tipo): Int

    private fun exibirErroRegistroTransacao(ex: Exception) {
        Toast.makeText(
            context,
            "${context.getString(R.string.mensagem_erro_add_transacao)} ${ex.message}",
            Toast.LENGTH_SHORT

        ).show()
    }

    private fun criarTransacao(tipo: Tipo): Transacao {

        val valorText = valorText.text.toString()
        val dataTexto = dataText.text.toString()
        val categoria =
            categoriaSpinner.selectedItem.toString()

        val transacao = Transacao(
            valor = BigDecimal(valorText),
            categoria = categoria,
            tipo = tipo,
            data = Calendar.getInstance().setDDMMYYYY(dataTexto)
        )
        return transacao
    }

   protected open fun configurarCategoria(tipo: Tipo) {
        val categorias = categoriasPorTipo(tipo)

        val adapter = ArrayAdapter
            .createFromResource(
                context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item
            )

        categoriaSpinner.adapter = adapter
    }

    protected fun categoriasPorTipo(tipo: Tipo): Int {
        if (Tipo.RECEITA == tipo) {
            return R.array.categorias_de_receita
        } else {
            return R.array.categorias_de_despesa
        }
    }

    protected open fun configurarValor() {
    }

    protected open fun configurarData(calendar: Calendar = Calendar.getInstance()) {

        with(dataText) {
            setText(calendar.formatarData())

            setOnClickListener {
                DatePickerDialog(context,
                    { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        dataText
                            .setText(dataSelecionada.formatarData())
                    }
                    ,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH))
                    .show()
            }
        }
    }

    private fun criarView(): View {
        return LayoutInflater.from(context)
            .inflate(
                R.layout.form_transacao,
                viewGroup,
                false
            )
    }
}