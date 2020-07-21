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
import br.com.renato.financask.delegade.TransacaoDelegate
import br.com.renato.financask.extension.formatarData
import br.com.renato.financask.extension.setDDMMYYYY
import br.com.renato.financask.model.Tipo
import br.com.renato.financask.model.Transacao
import kotlinx.android.synthetic.main.activity_lista_transacoes.view.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCriada = criarView()
    private val categoriaSpinner = viewCriada.form_transacao_categoria
    private val valorText = viewCriada.form_transacao_valor
    private val dataText = viewCriada.form_transacao_data

    fun exibir(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        configurarData()
        configurarCategoria(tipo)
        configurarFormulario(
            tipo,
            transacaoDelegate
        )
    }

    private fun configurarFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        val titulo = tituloPorTipo(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(
                context.getString(R.string.adicionar),
                { alerta, opcao ->

                    try {
                        val transacao = criarTransacao(tipo)

                        transacaoDelegate.delegate(transacao)
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

    private fun tituloPorTipo(tipo: Tipo): Int {
        if (Tipo.RECEITA == tipo) {
            return R.string.adiciona_receita
        } else {
            return R.string.adiciona_despesa
        }
    }

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

    private fun configurarCategoria(tipo: Tipo) {
        val categorias = categoriasPorTipo(tipo)

        val adapter = ArrayAdapter
            .createFromResource(
                context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item
            )

        categoriaSpinner.adapter = adapter
    }

    private fun categoriasPorTipo(tipo: Tipo): Int {
        if (Tipo.RECEITA == tipo) {
            return R.array.categorias_de_receita
        } else {
            return R.array.categorias_de_despesa
        }
    }

    private fun configurarData() {
        val hoje = Calendar.getInstance()
        with(dataText) {
            setText(hoje.formatarData())

            setOnClickListener {
                DatePickerDialog(context,
                    { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        dataText
                            .setText(dataSelecionada.formatarData())
                    }
                    ,
                    hoje.get(Calendar.YEAR),
                    hoje.get(Calendar.MONTH),
                    hoje.get(Calendar.DAY_OF_MONTH))
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